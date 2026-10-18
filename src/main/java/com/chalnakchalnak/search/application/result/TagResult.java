package com.chalnakchalnak.search.application.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagResult {

  @JsonProperty("tagId")
  private Long tagId;

  @JsonProperty("name")
  private String tagName;

  @Builder
  public TagResult(Long tagId, String tagName) {
    this.tagId = tagId;
    this.tagName = tagName;
  }
}
