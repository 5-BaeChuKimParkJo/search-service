package com.chalnakchalnak.search.adapter.in.web.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionSearchPageResponse {

  private List<AuctionSearchResponse> getAuctionSearchResponseVoList;
  private AuctionCursorResponse nextCursorVo;

  @Builder
  public AuctionSearchPageResponse(
      List<AuctionSearchResponse> getAuctionSearchResponseVoList,
      AuctionCursorResponse nextCursorVo) {
    this.getAuctionSearchResponseVoList = getAuctionSearchResponseVoList;
    this.nextCursorVo = nextCursorVo;
  }
}
