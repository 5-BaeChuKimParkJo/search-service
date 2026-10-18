package com.chalnakchalnak.search.application.converter;

import com.chalnakchalnak.search.application.command.AuctionIndexCommand;
import com.chalnakchalnak.search.application.command.UpsertAuctionCommand;
import com.chalnakchalnak.search.application.result.CategoryResult;
import com.chalnakchalnak.search.application.result.TagResult;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AuctionBatchEventConverter {

  public UpsertAuctionCommand toAuctionUpsertEventDto(
      AuctionIndexCommand auctionBatchEventDto,
      CategoryResult categoryResponseDto,
      List<TagResult> tagResponseDtos) {
    return toAuctionUpsertEventDto(
        auctionBatchEventDto,
        categoryResponseDto.getCategoryId(),
        categoryResponseDto.getCategoryName(),
        tagResponseDtos.stream().map(TagResult::getTagName).toList());
  }

  public UpsertAuctionCommand toAuctionUpsertEventDto(AuctionIndexCommand auctionBatchEventDto) {
    String categoryName = auctionBatchEventDto.getCategoryName();
    if (categoryName == null || categoryName.isBlank()) {
      categoryName = "category-" + auctionBatchEventDto.getCategoryId();
    }

    List<String> tagNames = auctionBatchEventDto.getTagNames();
    if (tagNames == null || tagNames.isEmpty()) {
      tagNames =
          auctionBatchEventDto.getTagIds() == null
              ? List.of()
              : auctionBatchEventDto.getTagIds().stream().map(tagId -> "tag-" + tagId).toList();
    }

    return toAuctionUpsertEventDto(
        auctionBatchEventDto, auctionBatchEventDto.getCategoryId(), categoryName, tagNames);
  }

  private UpsertAuctionCommand toAuctionUpsertEventDto(
      AuctionIndexCommand auctionBatchEventDto,
      int categoryId,
      String categoryName,
      List<String> tagNames) {
    return UpsertAuctionCommand.builder()
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
