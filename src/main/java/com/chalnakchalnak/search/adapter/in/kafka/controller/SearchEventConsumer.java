package com.chalnakchalnak.search.adapter.in.kafka.controller;

import com.chalnakchalnak.search.adapter.in.kafka.event.auction.AuctionEvent;
import com.chalnakchalnak.search.adapter.in.kafka.event.product.ProductEvent;
import com.chalnakchalnak.search.adapter.in.kafka.mapper.AuctionMessageMapper;
import com.chalnakchalnak.search.adapter.in.kafka.mapper.ProductMessageMapper;
import com.chalnakchalnak.search.application.port.in.AuctionSearchUseCase;
import com.chalnakchalnak.search.application.port.in.KeywordSearchUseCase;
import com.chalnakchalnak.search.application.port.in.ProductSearchUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchEventConsumer {

  private final AuctionSearchUseCase auctionSearchUseCase;
  private final KeywordSearchUseCase keywordSearchUseCase;
  private final ProductSearchUseCase productSearchUseCase;
  private final ObjectMapper objectMapper;
  private final AuctionMessageMapper auctionMapper;
  private final ProductMessageMapper productMapper;

  @KafkaListener(
      topics = "auction-service.outbox",
      containerFactory = "kafkaListenerContainerFactory")
  public void consumeAuctionSearch(
      @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
      @Payload List<String> messages,
      Acknowledgment ack)
      throws JsonProcessingException {

    List<AuctionEvent> auctionEventList =
        objectMapper.readValue(
            messages.toString(),
            objectMapper.getTypeFactory().constructCollectionType(List.class, AuctionEvent.class));

    log.info("Received messages: {}", messages);

    try {
      var commands =
          auctionEventList.stream()
              .map(event -> auctionMapper.toAuctionBatchEventDto(event.getPayload(), event.getOp()))
              .toList();
      auctionSearchUseCase.saveAuctionBulk(commands);
      keywordSearchUseCase.saveKeywordBulk(
          commands.stream().map(command -> command.getTitle()).toList());
      ack.acknowledge();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @KafkaListener(
      topics = "product-service.outbox",
      containerFactory = "kafkaListenerContainerFactory")
  public void consumeProductSearch(
      @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
      @Payload List<String> messages,
      Acknowledgment ack)
      throws JsonProcessingException {

    List<ProductEvent> productEvents =
        objectMapper.readValue(
            messages.toString(),
            objectMapper.getTypeFactory().constructCollectionType(List.class, ProductEvent.class));

    try {
      log.info("Received product messages: {}", messages);
      var commands =
          new java.util.ArrayList<
              com.chalnakchalnak.search.application.command.ProductIndexCommand>();
      for (var event : productEvents) {
        commands.add(productMapper.toProductBatchEventDto(event.getPayload(), event.getOp()));
      }
      productSearchUseCase.saveProductBulk(commands);
      keywordSearchUseCase.saveProductKeywordBulk(
          commands.stream().map(command -> command.getTitle()).toList());
      ack.acknowledge();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
