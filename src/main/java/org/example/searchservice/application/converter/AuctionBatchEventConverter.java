package org.example.searchservice.application.converter;


import org.example.searchservice.application.dto.in.AuctionBatchEventDto;
import org.example.searchservice.application.dto.in.AuctionUpsertEventDto;
import org.example.searchservice.application.dto.in.CategoryResponseDto;
import org.example.searchservice.application.dto.in.TagResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuctionBatchEventConverter {

    public AuctionUpsertEventDto toAuctionUpsertEventDto(AuctionBatchEventDto auctionBatchEventDto, CategoryResponseDto categoryResponseDto, List<TagResponseDto> tagResponseDtos) {
        return AuctionUpsertEventDto.builder()
                .endAt(auctionBatchEventDto.getEndAt())
                .title(auctionBatchEventDto.getTitle())
                .images(auctionBatchEventDto.getImages())
                .tagIds(auctionBatchEventDto.getTagIds())
                .startAt(auctionBatchEventDto.getStartAt())
                .version(auctionBatchEventDto.getVersion())
                .createdAt(auctionBatchEventDto.getCreatedAt())
                .viewCount(auctionBatchEventDto.getViewCount())
                .categoryId(categoryResponseDto.getCategoryId())
                .currentBid(auctionBatchEventDto.getCurrentBid())
                .minimumBid(auctionBatchEventDto.getMinimumBid())
                .sellerUuid(auctionBatchEventDto.getSellerUuid())
                .auctionUuid(auctionBatchEventDto.getAuctionUuid())
                .description(auctionBatchEventDto.getDescription())
                .isDirectDeal(auctionBatchEventDto.isDirectDeal())
                .thumbnailKey(auctionBatchEventDto.getThumbnailKey())
                .thumbnailUrl(auctionBatchEventDto.getThumbnailUrl())
                .productCondition(auctionBatchEventDto.getProductCondition())
                .directDealLocation(auctionBatchEventDto.getDirectDealLocation())
                .categoryName(categoryResponseDto.getCategoryName())
                .tagNames(tagResponseDtos.stream().map(TagResponseDto::getTagName).toList())
                .build();
    }
}
