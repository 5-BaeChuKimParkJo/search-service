package org.example.searchservice.adapter.in.kafka.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.adapter.in.kafka.event.AuctionCreateEvent;
import org.example.searchservice.adapter.in.kafka.event.AuctionEvent;
import org.example.searchservice.adapter.in.kafka.event.Image;
import org.example.searchservice.adapter.in.kafka.mapper.AuctionMessageMapper;
import org.example.searchservice.application.dto.in.AuctionCreateEventDto;
import org.example.searchservice.application.port.in.AuctionSearchUseCase;
import org.example.searchservice.application.port.in.KeywordSearchUseCase;
import org.example.searchservice.common.exception.BaseException;
import org.example.searchservice.common.response.BaseResponseStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumerController {

    private final AuctionSearchUseCase auctionSearchUseCase;
    private final AuctionMessageMapper auctionMessageMapper;
    private final KeywordSearchUseCase keywordSearchUseCase;
    private final ObjectMapper objectMapper;


//    @KafkaListener(topics = "auction-service.outbox", containerFactory = "kafkaListenerContainerFactory")
//    public void consumeAuctionSearch( @Header(KafkaHeaders.RECEIVED_KEY) String key,
//                                     @Payload String message) throws JsonProcessingException {
//
//        log.info("Received message: {}", message);
//        log.info("Received key: {}", key);
//
//        AuctionEvent auctionEvent = objectMapper.readValue(message, AuctionEvent.class);
//
//
//        AuctionCreateEventDto auctionCreateEventDto = auctionMessageMapper.toAuctionCreateEventDto(message);
//        auctionSearchUseCase.saveAuction(auctionCreateEventDto);
//    }
//    }

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
}
