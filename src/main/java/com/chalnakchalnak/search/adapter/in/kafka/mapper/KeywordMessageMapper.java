package com.chalnakchalnak.search.adapter.in.kafka.mapper;

import com.chalnakchalnak.search.adapter.in.kafka.event.KeywordBatchEvent;
import com.chalnakchalnak.search.adapter.in.kafka.event.auction.AuctionEvent;
import com.chalnakchalnak.search.adapter.in.kafka.event.product.ProductBatchEvent;
import com.chalnakchalnak.search.adapter.in.kafka.event.product.ProductEvent;
import com.chalnakchalnak.search.application.command.IndexKeywordCommand;
import com.chalnakchalnak.search.common.exception.BaseException;
import com.chalnakchalnak.search.common.response.BaseResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeywordMessageMapper {

  private final ObjectMapper objectMapper;

  public KeywordBatchEvent toKeywordBatchEvent(AuctionEvent auctionEvent) {

    try {
      return objectMapper.readValue(auctionEvent.getPayload(), KeywordBatchEvent.class);
    } catch (Exception e) {
      throw new BaseException(BaseResponseStatus.FAILED_TO_PARSE_KEYWORD_BATCH_EVENT);
    }
  }

  public IndexKeywordCommand toKeywordBatchEventDto(String keyword, int weight) {
    return IndexKeywordCommand.builder().keyword(keyword).weight(weight).build();
  }

  public ProductBatchEvent toProductBatchEvent(ProductEvent productEvent) {
    return null;
  }
}
