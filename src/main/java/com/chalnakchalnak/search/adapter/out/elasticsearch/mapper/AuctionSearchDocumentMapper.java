package com.chalnakchalnak.search.adapter.out.elasticsearch.mapper;

import com.chalnakchalnak.search.adapter.out.elasticsearch.entity.AuctionSearchDocument;
import com.chalnakchalnak.search.application.command.CreateAuctionIndexCommand;
import com.chalnakchalnak.search.application.command.IndexAuctionCommand;
import com.chalnakchalnak.search.application.command.UpsertAuctionCommand;
import com.chalnakchalnak.search.application.result.AuctionSearchResult;
import com.chalnakchalnak.search.application.result.AuctionSuggestionResult;
import com.chalnakchalnak.search.application.result.CategoryResult;
import com.chalnakchalnak.search.application.result.TagResult;
import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AuctionSearchDocumentMapper {
  public AuctionSearchDocument toAuctionSearchDocument(
      CreateAuctionIndexCommand createAuctionSearchRequestDto) {
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

  public AuctionSearchDocument toAuctionSearchDocument(UpsertAuctionCommand dto) {
    return AuctionSearchDocument.builder()
        .thumbnailKey(dto.getThumbnailKey())
        .createdAt(dto.getCreatedAt())
        .endAt(dto.getEndAt())
        .startAt(dto.getStartAt())
        .version(dto.getVersion())
        .viewCount(dto.getViewCount())
        .currentBid(dto.getCurrentBid())
        .minimumBid(dto.getMinimumBid())
        .sellerUuid(dto.getSellerUuid())
        .isDirectDeal(dto.isDirectDeal())
        .productCondition(dto.getProductCondition())
        .thumbnailUrl(dto.getThumbnailUrl())
        .directDealLocation(dto.getDirectDealLocation())
        .images(dto.getImages())
        .auctionTitle(dto.getTitle())
        .auctionDescription(dto.getDescription())
        .auctionUuid(dto.getAuctionUuid())
        .categoryId(dto.getCategoryId())
        .categoryName(dto.getCategoryName())
        .tagId(dto.getTagIds())
        .tagNames(dto.getTagNames())
        .build();
  }

  public AuctionSearchDocument toAuctionSearchDocument(
      IndexAuctionCommand auctionCreateEventDto,
      CategoryResult categoryResponseDto,
      List<TagResult> tagResponseDtoList) {

    List<Long> tagIds = tagResponseDtoList.stream().map(TagResult::getTagId).toList();

    List<String> tagNames = tagResponseDtoList.stream().map(TagResult::getTagName).toList();

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

  public AuctionSearchResult toGetAuctionSearchResponseDto(
      AuctionSearchDocument auctionSearchDocument) {
    return AuctionSearchResult.builder()
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

  public AuctionSuggestionResult toSuggestAuctionSearchResponseDto(String auctionTitle) {
    return AuctionSuggestionResult.builder().keyword(auctionTitle).build();
  }
}
