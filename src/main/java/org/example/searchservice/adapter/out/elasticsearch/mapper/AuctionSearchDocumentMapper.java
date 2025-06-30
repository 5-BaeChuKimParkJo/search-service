package org.example.searchservice.adapter.out.elasticsearch.mapper;


import lombok.Getter;
import org.example.searchservice.adapter.out.elasticsearch.entity.AuctionSearchDocument;
import org.example.searchservice.application.dto.in.AuctionCreateEventDto;
import org.example.searchservice.application.dto.in.CategoryResponseDto;
import org.example.searchservice.application.dto.in.CreateAuctionSearchRequestDto;
import org.example.searchservice.application.dto.in.TagResponseDto;
import org.example.searchservice.application.dto.out.GetAuctionSearchResponseDto;
import org.example.searchservice.application.dto.out.SuggestAuctionSearchResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
public class AuctionSearchDocumentMapper {
    public AuctionSearchDocument toAuctionSearchDocument(CreateAuctionSearchRequestDto createAuctionSearchRequestDto) {
        return AuctionSearchDocument.builder()
                .auctionUuid(createAuctionSearchRequestDto.getAuctionUuid())
                .auctionTitle(createAuctionSearchRequestDto.getAuctionTitle())
                .auctionDescription(createAuctionSearchRequestDto.getDescription())
                .status(createAuctionSearchRequestDto.getStatus())
                .thumbnailKey(createAuctionSearchRequestDto.getThumbnailKey())
                .directDealLocation(createAuctionSearchRequestDto.getDirectDealLocation())
                .createdAt(java.time.Instant.now())
                .build();
    }

    public AuctionSearchDocument toAuctionSearchDocument(AuctionCreateEventDto auctionCreateEventDto, CategoryResponseDto categoryResponseDto, List<TagResponseDto> tagResponseDtoList) {

        List<Long> tagIds = tagResponseDtoList.stream()
                .map(TagResponseDto::getTagId)
                .toList();

        List<String> tagNames = tagResponseDtoList.stream()
                .map(TagResponseDto::getTagName)
                .toList();

        return AuctionSearchDocument.builder()
                .thumbnailKey(auctionCreateEventDto.getThumbnailKey())
                .createdAt(auctionCreateEventDto.getCreatedAt())
                .endAt(auctionCreateEventDto.getEndAt())
                .startAt(auctionCreateEventDto.getStartAt())
                .version(auctionCreateEventDto.getVersion())
                .viewCount(auctionCreateEventDto.getViewCount())
                .currentBid(auctionCreateEventDto.getCurrentBid())
                .minimumBid(auctionCreateEventDto.getMinimumBid())
                .sellerUuid(auctionCreateEventDto.getSellerUuid())
                .isDirectDeal(auctionCreateEventDto.isDirectDeal())
                .productCondition(auctionCreateEventDto.getProductCondition())
                .thumbnailUrl(auctionCreateEventDto.getThumbnailUrl())
                .directDealLocation(auctionCreateEventDto.getDirectDealLocation())
                .thumbnailUrl(auctionCreateEventDto.getThumbnailUrl())
                .images(auctionCreateEventDto.getImages())
                .auctionTitle(auctionCreateEventDto.getTitle())
                .auctionDescription(auctionCreateEventDto.getDescription())
                .auctionUuid(auctionCreateEventDto.getAuctionUuid())
                .categoryId(categoryResponseDto.getCategoryId())
                .categoryName(categoryResponseDto.getCategoryName())
                .categoryDescription(categoryResponseDto.getCategoryDescription())
                .categoryThumbnailKey(categoryResponseDto.getCategoryImageUrl())
                .tagId(tagIds)
                .tagNames(tagNames)
                .build();
    }

    public GetAuctionSearchResponseDto toGetAuctionSearchResponseDto(AuctionSearchDocument auctionSearchDocument) {
        return GetAuctionSearchResponseDto.builder()
                .id(auctionSearchDocument.getId())
                .auctionUuid(auctionSearchDocument.getAuctionUuid())
                .auctionTitle(auctionSearchDocument.getAuctionTitle())
                .auctionDescription(auctionSearchDocument.getAuctionDescription())
                .status(auctionSearchDocument.getStatus())
                .thumbnailKey(auctionSearchDocument.getThumbnailKey())
                .directDealLocation(auctionSearchDocument.getDirectDealLocation())
                .thumbnailUrl(auctionSearchDocument.getThumbnailUrl())
                .images(auctionSearchDocument.getImages())
                .sellerUuid(auctionSearchDocument.getSellerUuid())
                .startAt(auctionSearchDocument.getStartAt())
                .endAt(auctionSearchDocument.getEndAt())
                .version(auctionSearchDocument.getVersion())
                .createdAt(auctionSearchDocument.getCreatedAt())
                .viewCount(auctionSearchDocument.getViewCount())
                .currentBid(auctionSearchDocument.getCurrentBid())
                .minimumBid(auctionSearchDocument.getMinimumBid())
                .description(auctionSearchDocument.getDescription())
                .isDirectDeal(auctionSearchDocument.isDirectDeal())
                .productCondition(auctionSearchDocument.getProductCondition())
                .categoryId(auctionSearchDocument.getCategoryId())
                .categoryName(auctionSearchDocument.getCategoryName())
                .categoryDescription(auctionSearchDocument.getCategoryDescription())
                .categoryThumbnailKey(auctionSearchDocument.getCategoryThumbnailKey())
                .tagId(auctionSearchDocument.getTagId())
                .tagNames(auctionSearchDocument.getTagNames())
                .build();
    }

    public SuggestAuctionSearchResponseDto toSuggestAuctionSearchResponseDto(String auctionTitle) {
        return SuggestAuctionSearchResponseDto.builder()
                .keyword(auctionTitle)
                .build();
    }

}
