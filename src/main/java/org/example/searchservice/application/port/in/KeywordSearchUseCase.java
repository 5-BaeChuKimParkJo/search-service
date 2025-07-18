package org.example.searchservice.application.port.in;

import org.example.searchservice.adapter.in.kafka.event.auction.AuctionEvent;
import org.example.searchservice.adapter.in.kafka.event.product.ProductEvent;
import org.example.searchservice.application.dto.out.SuggestAuctionSearchResponseDto;

import java.util.List;

public interface KeywordSearchUseCase {

    List<SuggestAuctionSearchResponseDto> suggestKeywords(String keyword);

    void saveKeywordBulk(List<AuctionEvent> auctionEventList);

    void saveProductKeywordBulk(List<ProductEvent> productEvents);


}
