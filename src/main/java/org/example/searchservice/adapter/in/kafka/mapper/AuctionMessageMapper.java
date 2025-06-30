package org.example.searchservice.adapter.in.kafka.mapper;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.searchservice.adapter.in.kafka.event.AuctionEvent;
import org.example.searchservice.application.dto.in.AuctionCreateEventDto;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Component
@RequiredArgsConstructor
public class AuctionMessageMapper {

    private final ObjectMapper objectMapper;

    public AuctionCreateEventDto toAuctionCreateEventDto(String message) {

       try {

           AuctionEvent auctionEvent = objectMapper.readValue(message, AuctionEvent.class);

           return objectMapper.readValue(
                   auctionEvent.getPayload(),
                   AuctionCreateEventDto.class
           );

       }

       catch (Exception e) {
           throw new RuntimeException("Failed to parse message: " + message, e);
       }
    }


}
