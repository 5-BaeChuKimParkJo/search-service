package org.example.searchservice.adapter.out.elasticsearch.mapper;


import lombok.Getter;
import org.example.searchservice.adapter.out.elasticsearch.entity.AuctionSearchDocument;
import org.example.searchservice.application.dto.in.CreateAuctionSearchRequestDto;
import org.example.searchservice.application.dto.out.GetAuctionSearchResponseDto;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AuctionSearchDocumentMapper {
    public AuctionSearchDocument toAuctionSearchDocument(CreateAuctionSearchRequestDto createAuctionSearchRequestDto) {
        return AuctionSearchDocument.builder()
                .auctionUuid(createAuctionSearchRequestDto.getAuctionUuid())
                .title(createAuctionSearchRequestDto.getTitle())
                .description(createAuctionSearchRequestDto.getDescription())
                .status(createAuctionSearchRequestDto.getStatus())
                .thumbnailKey(createAuctionSearchRequestDto.getThumbnailKey())
                .directDealLocation(createAuctionSearchRequestDto.getDirectDealLocation())
                .build();
    }

    public GetAuctionSearchResponseDto toGetAuctionSearchResponseDto(AuctionSearchDocument auctionSearchDocument) {
        return GetAuctionSearchResponseDto.builder()
                .auctionUuid(auctionSearchDocument.getAuctionUuid())
                .title(auctionSearchDocument.getTitle())
                .description(auctionSearchDocument.getDescription())
                .status(auctionSearchDocument.getStatus())
                .thumbnailKey(auctionSearchDocument.getThumbnailKey())
                .directDealLocation(auctionSearchDocument.getDirectDealLocation())
                .build();
    }
}
