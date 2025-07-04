package org.example.searchservice.adapter.out.elasticsearch.mapper;


import org.example.searchservice.adapter.out.elasticsearch.entity.KeywordSearchDocument;
import org.example.searchservice.application.dto.in.AuctionCreateEventDto;
import org.example.searchservice.application.dto.in.KeywordBatchEventDto;
import org.example.searchservice.application.dto.out.SuggestAuctionSearchResponseDto;
import org.springframework.stereotype.Component;

@Component
public class KeywordSearchDocumentMapper {

    public KeywordSearchDocument toKeywordSearchDocument(AuctionCreateEventDto auctionCreateEventDto) {
        return KeywordSearchDocument.builder()
                .keyword(auctionCreateEventDto.getTitle())
                .build();
    }

    public KeywordSearchDocument toKeywordSearchDocument(KeywordBatchEventDto keywordBatchEventDto) {
        return KeywordSearchDocument.builder()
                .keyword(keywordBatchEventDto.getKeyword())
                .weight(keywordBatchEventDto.getWeight())
                .build();
    }

    public SuggestAuctionSearchResponseDto toSuggestAuctionSearchResponseDto(KeywordSearchDocument keywordSearchDocument) {
        return SuggestAuctionSearchResponseDto.builder()
                .keyword(keywordSearchDocument.getKeyword())
                .weight(keywordSearchDocument.getWeight())
                .build();
    }

}
