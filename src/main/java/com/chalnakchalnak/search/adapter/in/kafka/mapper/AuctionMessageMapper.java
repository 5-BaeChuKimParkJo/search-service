package com.chalnakchalnak.search.adapter.in.kafka.mapper;

import com.chalnakchalnak.search.adapter.in.kafka.event.auction.AuctionBatchEvent;
import com.chalnakchalnak.search.adapter.in.kafka.event.auction.AuctionEvent;
import com.chalnakchalnak.search.application.command.AuctionIndexCommand;
import com.chalnakchalnak.search.application.command.DeleteAuctionCommand;
import com.chalnakchalnak.search.application.command.IndexAuctionCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class AuctionMessageMapper {

  private final ObjectMapper objectMapper;

  public IndexAuctionCommand toAuctionCreateEventDto(String message) {

    try {

      AuctionEvent auctionEvent = objectMapper.readValue(message, AuctionEvent.class);

      return objectMapper.readValue(auctionEvent.getPayload(), IndexAuctionCommand.class);

    } catch (Exception e) {
      throw new RuntimeException("Failed to parse message: " + message, e);
    }
  }

  public IndexAuctionCommand toAuctionCreateEventDtoV2(AuctionEvent auctionEvent) {

    try {
      return objectMapper.readValue(auctionEvent.getPayload(), IndexAuctionCommand.class);
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse message: " + auctionEvent, e);
    }
  }

  public AuctionIndexCommand toAuctionBatchEventDto(String payload, String op) {

    try {

      AuctionIndexCommand auctionBatchEventDto =
          objectMapper.readValue(payload, AuctionIndexCommand.class);
      auctionBatchEventDto.setOp(op);

      return auctionBatchEventDto;

    } catch (Exception e) {
      throw new RuntimeException("Failed to parse message: " + payload, e);
    }
  }

  public DeleteAuctionCommand toAuctionDeleteEventDto(String auctionUuid) {

    try {
      return DeleteAuctionCommand.builder().auctionUuid(auctionUuid).build();

    } catch (Exception e) {
      throw new RuntimeException("Failed to parse message: " + auctionUuid, e);
    }
  }

  public AuctionBatchEvent toAuctionDeleteEvent(AuctionEvent auctionEvent) {

    try {
      return objectMapper.readValue(auctionEvent.getPayload(), AuctionBatchEvent.class);
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse message: " + auctionEvent, e);
    }
  }
}
