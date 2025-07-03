package org.example.searchservice.adapter.in.kafka.mapper;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.searchservice.adapter.in.kafka.event.AuctionBatchEvent;
import org.example.searchservice.adapter.in.kafka.event.AuctionEvent;
import org.example.searchservice.application.dto.in.AuctionBatchEventDto;
import org.example.searchservice.application.dto.in.AuctionCreateEventDto;
import org.example.searchservice.application.dto.in.AuctionDeleteEventDto;
import org.springframework.stereotype.Component;

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

    public AuctionCreateEventDto toAuctionCreateEventDtoV2(AuctionEvent auctionEvent) {

        try {
            return objectMapper.readValue(
                    auctionEvent.getPayload(),
                    AuctionCreateEventDto.class
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse message: " + auctionEvent, e);
        }
    }

    public AuctionBatchEventDto toAuctionBatchEventDto(String payload, String op) {

        try {

            AuctionBatchEventDto auctionBatchEventDto = objectMapper.readValue(
                    payload,
                    AuctionBatchEventDto.class
            );
            auctionBatchEventDto.setOp(op);

            return auctionBatchEventDto;

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse message: " + payload, e);
        }
    }

    public AuctionDeleteEventDto toAuctionDeleteEventDto(String auctionUuid) {

        try {
            return AuctionDeleteEventDto.builder()
                    .auctionUuid(auctionUuid)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse message: " + auctionUuid, e);
        }
    }

    public AuctionBatchEvent toAuctionDeleteEvent(AuctionEvent auctionEvent) {

        try {
            return objectMapper.readValue(
                    auctionEvent.getPayload(),
                    AuctionBatchEvent.class
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse message: " + auctionEvent, e);
        }
    }


}
