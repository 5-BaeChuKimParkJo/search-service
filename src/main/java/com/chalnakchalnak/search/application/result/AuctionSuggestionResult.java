package com.chalnakchalnak.search.application.result;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionSuggestionResult {

  private String keyword;
  private int weight;

  @Builder
  public AuctionSuggestionResult(String keyword, int weight) {
    this.keyword = keyword;
    this.weight = weight;
  }
}
