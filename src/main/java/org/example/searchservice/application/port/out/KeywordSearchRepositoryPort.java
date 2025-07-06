package org.example.searchservice.application.port.out;

import org.example.searchservice.application.dto.in.AuctionCreateEventDto;
import org.example.searchservice.application.dto.in.KeywordBatchEventDto;
import org.example.searchservice.application.dto.out.SuggestAuctionSearchResponseDto;

import java.util.List;

public interface KeywordSearchRepositoryPort {

    void saveKeyword(AuctionCreateEventDto auctionCreateEventDto);

    List<SuggestAuctionSearchResponseDto> suggestAuctionSearch(String keyword);

    void saveKeywordBulk(List<KeywordBatchEventDto> keywordBatchEventDtos);

}
