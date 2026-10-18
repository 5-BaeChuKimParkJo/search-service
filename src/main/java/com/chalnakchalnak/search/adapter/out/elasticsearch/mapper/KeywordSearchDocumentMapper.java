package com.chalnakchalnak.search.adapter.out.elasticsearch.mapper;

import com.chalnakchalnak.search.adapter.out.elasticsearch.entity.KeywordSearchDocument;
import com.chalnakchalnak.search.application.command.IndexAuctionCommand;
import com.chalnakchalnak.search.application.command.IndexKeywordCommand;
import com.chalnakchalnak.search.application.result.AuctionSuggestionResult;
import org.springframework.stereotype.Component;

@Component
public class KeywordSearchDocumentMapper {

  public KeywordSearchDocument toKeywordSearchDocument(IndexAuctionCommand auctionCreateEventDto) {
    return KeywordSearchDocument.builder().keyword(auctionCreateEventDto.getTitle()).build();
  }

  public KeywordSearchDocument toKeywordSearchDocument(IndexKeywordCommand keywordBatchEventDto) {
    return KeywordSearchDocument.builder()
        .keyword(keywordBatchEventDto.getKeyword())
        .weight(keywordBatchEventDto.getWeight())
        .build();
  }

  public AuctionSuggestionResult toSuggestAuctionSearchResponseDto(
      KeywordSearchDocument keywordSearchDocument) {
    return AuctionSuggestionResult.builder()
        .keyword(keywordSearchDocument.getKeyword())
        .weight(keywordSearchDocument.getWeight())
        .build();
  }
}
