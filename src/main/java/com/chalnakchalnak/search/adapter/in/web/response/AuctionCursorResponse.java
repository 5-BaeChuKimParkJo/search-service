package com.chalnakchalnak.search.adapter.in.web.response;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionCursorResponse {

  private String lastAuctionUuid;
  private Instant lastAuctionCreatedAt;
  private int lastAuctionCurrentBid;
  private int lastAuctionViewCount;

  @Builder
  public AuctionCursorResponse(
      String lastAuctionUuid,
      Instant lastAuctionCreatedAt,
      int lastAuctionCurrentBid,
      int lastAuctionViewCount) {
    this.lastAuctionUuid = lastAuctionUuid;
    this.lastAuctionCreatedAt = lastAuctionCreatedAt;
    this.lastAuctionCurrentBid = lastAuctionCurrentBid;
    this.lastAuctionViewCount = lastAuctionViewCount;
  }
}
