package com.chalnakchalnak.search.application.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IndexKeywordCommand {

  private String keyword;
  private int weight;

  @Builder
  public IndexKeywordCommand(String keyword, int weight) {
    this.keyword = keyword;
    this.weight = weight;
  }
}
