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
import org.example.searchservice.common.exception.BaseException;
import org.example.searchservice.common.response.BaseResponseStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
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
    private final AuctionMessageMapper auctionMessageMapper;
    private final ObjectMapper objectMapper;


    @KafkaListener(topics = "auction-service.outbox", containerFactory = "kafkaListenerContainerFactory")
    public void consumeAuctionSearch( @Header(KafkaHeaders.RECEIVED_KEY) String key,
                                     @Payload String message) throws JsonProcessingException {

        log.info("Received message: {}", message);
        log.info("Received key: {}", key);

        AuctionEvent auctionEvent = objectMapper.readValue(message, AuctionEvent.class);


        AuctionCreateEventDto auctionCreateEventDto = auctionMessageMapper.toAuctionCreateEventDto(message);
        auctionSearchUseCase.saveAuction(auctionCreateEventDto);
    }
    }

//    @KafkaListener(topics = "auction-service.outbox", containerFactory = "kafkaListenerContainerFactory")
//    public void consumeAuctionSearch( @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
//                                     @Payload List<String> messages) throws JsonProcessingException {
//
//            List<AuctionEvent> auctionEvents = objectMapper.readValue(message, AuctionEvent.class);
//
//            //
////            Map<String, AuctionEvent> map  = null
////            for (AuctionEvent auctionEvent:auctionEvents) {
////
////                map.put(auctionEvent.getAuctionUuid(), auctionEvent);
////            }
////
////            List<AuctionEvent> dList = null
////            List<AuctionEvent> cuList = null
////
////        for (AuctionEvent auctionEvent : map.values()) {
////
////                if (auctionEvent.getOp().equals("d")) {
//
////                    auctionSearchUseCase.deleteAuction(auctionEvent.getAuctionUuid());
////                } else {
////                    AuctionCreateEventDto auctionCreateEventDto = auctionMessageMapper.toAuctionCreateEventDto(auctionEvent);
////                    auctionSearchUseCase.saveAuction(auctionCreateEventDto);
////                }
////            }
////            AuctionCreateEventDto auctionCreateEventDto = auctionMessageMapper.toAuctionCreateEventDto(message);
////
////            if (auctionEvent.getOp() == "d") {
////                auctionSearchUseCase.deleteAuction(auctionCreateEventDto.getAuctionUuid());
////            }   else {
////                auctionSearchUseCase.saveAuction(auctionCreateEventDto);
////            }
//    }
//}
