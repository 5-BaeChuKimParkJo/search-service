package org.example.searchservice.adapter.in.kafka.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.adapter.in.kafka.event.auction.AuctionEvent;
import org.example.searchservice.adapter.in.kafka.event.product.ProductEvent;
import org.example.searchservice.application.port.in.AuctionSearchUseCase;
import org.example.searchservice.application.port.in.KeywordSearchUseCase;
import org.example.searchservice.application.port.in.ProductSearchUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumerController {

    private final AuctionSearchUseCase auctionSearchUseCase;
    private final KeywordSearchUseCase keywordSearchUseCase;
    private final ProductSearchUseCase productSearchUseCase;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "auction-service.outbox", containerFactory = "kafkaListenerContainerFactory")
    public void consumeAuctionSearch(@Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                                     @Payload List<String> messages, Acknowledgment ack) throws JsonProcessingException {

            List<AuctionEvent> auctionEventList= objectMapper.readValue(
                    messages.toString(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, AuctionEvent.class)
            );

            log.info("Received messages: {}", messages);

        try {
            auctionSearchUseCase.saveAuctionBulk(auctionEventList);
            keywordSearchUseCase.saveKeywordBulk(auctionEventList);
            ack.acknowledge();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @KafkaListener(topics = "product-service.outbox", containerFactory = "kafkaListenerContainerFactory")
    public void consumeProductSearch(@Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                                     @Payload List<String> messages, Acknowledgment ack) throws JsonProcessingException {

        List<ProductEvent> productEvents = objectMapper.readValue(
                messages.toString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, ProductEvent.class)
        );

        try {
            log.info("Received product messages: {}", messages);
            productSearchUseCase.saveProductBulk(productEvents);
            keywordSearchUseCase.saveProductKeywordBulk(productEvents);
            ack.acknowledge();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}
