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
        return toAuctionUpsertEventDto(
                auctionBatchEventDto,
                categoryResponseDto.getCategoryId(),
                categoryResponseDto.getCategoryName(),
                tagResponseDtos.stream().map(TagResponseDto::getTagName).toList()
        );
    }

    public AuctionUpsertEventDto toAuctionUpsertEventDto(AuctionBatchEventDto auctionBatchEventDto) {
        String categoryName = auctionBatchEventDto.getCategoryName();
        if (categoryName == null || categoryName.isBlank()) {
            categoryName = "category-" + auctionBatchEventDto.getCategoryId();
        }

        List<String> tagNames = auctionBatchEventDto.getTagNames();
        if (tagNames == null || tagNames.isEmpty()) {
            tagNames = auctionBatchEventDto.getTagIds() == null
                    ? List.of()
                    : auctionBatchEventDto.getTagIds().stream().map(tagId -> "tag-" + tagId).toList();
        }

        return toAuctionUpsertEventDto(
                auctionBatchEventDto,
                auctionBatchEventDto.getCategoryId(),
                categoryName,
                tagNames
        );
    }

    private AuctionUpsertEventDto toAuctionUpsertEventDto(
            AuctionBatchEventDto auctionBatchEventDto,
            int categoryId,
            String categoryName,
            List<String> tagNames
    ) {
        return AuctionUpsertEventDto.builder()
                .endAt(auctionBatchEventDto.getEndAt())
                .title(auctionBatchEventDto.getTitle())
                .images(auctionBatchEventDto.getImages())
                .tagIds(auctionBatchEventDto.getTagIds())
                .startAt(auctionBatchEventDto.getStartAt())
                .version(auctionBatchEventDto.getVersion())
                .createdAt(auctionBatchEventDto.getCreatedAt())
                .viewCount(auctionBatchEventDto.getViewCount())
                .categoryId(categoryId)
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
                .categoryName(categoryName)
                .tagNames(tagNames)
                .build();
    }
}
