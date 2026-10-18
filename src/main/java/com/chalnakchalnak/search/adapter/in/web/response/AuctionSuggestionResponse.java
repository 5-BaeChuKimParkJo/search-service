package com.chalnakchalnak.search.adapter.in.web.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionSuggestionResponse {

  private String keyword;
  private int weight;

  @Builder
  public AuctionSuggestionResponse(String keyword, int weight) {
    this.keyword = keyword;
    this.weight = weight;
  }
}
