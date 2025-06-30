package org.example.searchservice.adapter.in.kafka.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.searchservice.adapter.in.kafka.event.AuctionCreateEvent;
import org.example.searchservice.adapter.in.kafka.event.AuctionEvent;
import org.example.searchservice.adapter.in.kafka.event.Image;
import org.example.searchservice.adapter.in.kafka.mapper.AuctionMessageMapper;
import org.example.searchservice.application.dto.in.AuctionCreateEventDto;
import org.example.searchservice.application.port.in.AuctionSearchUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumerController {

    private final AuctionSearchUseCase auctionSearchUseCase;
    private final AuctionMessageMapper auctionMessageMapper;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "auction-service.outbox")
    public void consumeAuctionSearch( @Header(KafkaHeaders.RECEIVED_KEY) String key,
                                     @Payload String message) throws JsonProcessingException {

        AuctionEvent event = objectMapper.readValue(message, AuctionEvent.class);
        System.out.println("Received message: " + event);
        AuctionCreateEvent auctionCreateEvent = objectMapper.readValue(event.getPayload(), AuctionCreateEvent.class);
        System.out.println("Received message: " + auctionCreateEvent);
        System.out.println("Received key: " + key);

        AuctionCreateEventDto auctionCreateEventDto = auctionMessageMapper.toAuctionCreateEventDto(message);

        auctionSearchUseCase.saveAuction(auctionCreateEventDto);

    }
}
