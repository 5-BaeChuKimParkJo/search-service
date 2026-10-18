package com.chalnakchalnak.search.adapter.in.web.mapper;

import com.chalnakchalnak.search.adapter.in.web.request.AuctionSearchRequest;
import com.chalnakchalnak.search.adapter.in.web.request.CreateAuctionIndexRequest;
import com.chalnakchalnak.search.adapter.in.web.response.AuctionCursorResponse;
import com.chalnakchalnak.search.adapter.in.web.response.AuctionSearchPageResponse;
import com.chalnakchalnak.search.adapter.in.web.response.AuctionSearchResponse;
import com.chalnakchalnak.search.adapter.in.web.response.AuctionSuggestionResponse;
import com.chalnakchalnak.search.application.command.CreateAuctionIndexCommand;
import com.chalnakchalnak.search.application.query.AuctionSearchQuery;
import com.chalnakchalnak.search.application.result.AuctionSearchResult;
import com.chalnakchalnak.search.application.result.AuctionSuggestionResult;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@NoArgsConstructor
@Component
public class AuctionSearchWebMapper {

  public CreateAuctionIndexCommand toCreateAuctionSearchRequestDto(
      CreateAuctionIndexRequest createAuctionSearchRequestVo) {
    return CreateAuctionIndexCommand.builder()
        .auctionUuid(createAuctionSearchRequestVo.getAuctionUuid())
        .auctionTitle(createAuctionSearchRequestVo.getAuctionTitle())
        .description(createAuctionSearchRequestVo.getDescription())
        .status(createAuctionSearchRequestVo.getStatus())
        .thumbnailKey(createAuctionSearchRequestVo.getThumbnailKey())
        .directDealLocation(createAuctionSearchRequestVo.getDirectDealLocation())
        .build();
  }

  public AuctionSearchQuery toGetAuctionSearchRequestDto(
      AuctionSearchRequest getAuctionSearchRequestVo) {
    return AuctionSearchQuery.builder()
        .auctionTitle(getAuctionSearchRequestVo.getAuctionTitle())
        .categoryName(getAuctionSearchRequestVo.getCategoryName())
        .status(getAuctionSearchRequestVo.getStatus())
        .tagNames(getAuctionSearchRequestVo.getTagNames())
        .isDirectDeal(getAuctionSearchRequestVo.isDirectDeal())
        .directDealLocation(getAuctionSearchRequestVo.getDirectDealLocation())
        .productCondition(getAuctionSearchRequestVo.getProductCondition())
        .sortBy(getAuctionSearchRequestVo.getSortBy())
        .searchAfter(getAuctionSearchRequestVo.getSearchAfter())
        .build();
  }

  public AuctionSearchResponse toGetAuctionSearchResponseVo(
      AuctionSearchResult getAuctionSearchResponseDto) {

    return AuctionSearchResponse.builder()
        .id(getAuctionSearchResponseDto.getId())
        .auctionUuid(getAuctionSearchResponseDto.getAuctionUuid())
        .auctionTitle(getAuctionSearchResponseDto.getAuctionTitle())
        .auctionDescription(getAuctionSearchResponseDto.getAuctionDescription())
        .status(getAuctionSearchResponseDto.getStatus())
        .thumbnailKey(getAuctionSearchResponseDto.getThumbnailKey())
        .directDealLocation(getAuctionSearchResponseDto.getDirectDealLocation())
        .thumbnailUrl(getAuctionSearchResponseDto.getThumbnailUrl())
        .images(getAuctionSearchResponseDto.getImages())
        .sellerUuid(getAuctionSearchResponseDto.getSellerUuid())
        .startAt(getAuctionSearchResponseDto.getStartAt())
        .endAt(getAuctionSearchResponseDto.getEndAt())
        .version(getAuctionSearchResponseDto.getVersion())
        .createdAt(getAuctionSearchResponseDto.getCreatedAt())
        .viewCount(getAuctionSearchResponseDto.getViewCount())
        .currentBid(getAuctionSearchResponseDto.getCurrentBid())
        .minimumBid(getAuctionSearchResponseDto.getMinimumBid())
        .description(getAuctionSearchResponseDto.getDescription())
        .isDirectDeal(getAuctionSearchResponseDto.isDirectDeal())
        .productCondition(getAuctionSearchResponseDto.getProductCondition())
        .categoryId(getAuctionSearchResponseDto.getCategoryId())
        .categoryName(getAuctionSearchResponseDto.getCategoryName())
        .categoryDescription(getAuctionSearchResponseDto.getCategoryDescription())
        .categoryThumbnailKey(getAuctionSearchResponseDto.getCategoryThumbnailKey())
        .tagId(getAuctionSearchResponseDto.getTagId())
        .tagNames(getAuctionSearchResponseDto.getTagNames())
        .build();
  }

  public AuctionSearchPageResponse toGetAuctionSearchResponseWrapperVo(
      List<AuctionSearchResult> dtoList) {

    List<AuctionSearchResponse> getAuctionSearchResponseVoList =
        dtoList.stream().map(this::toGetAuctionSearchResponseVo).toList();

    AuctionCursorResponse nextCursorVo =
        getAuctionSearchResponseVoList.isEmpty()
            ? null
            : AuctionCursorResponse.builder()
                .lastAuctionUuid(
                    getAuctionSearchResponseVoList
                        .get(getAuctionSearchResponseVoList.size() - 1)
                        .getAuctionUuid())
                .lastAuctionCreatedAt(
                    getAuctionSearchResponseVoList
                        .get(getAuctionSearchResponseVoList.size() - 1)
                        .getCreatedAt())
                .lastAuctionCurrentBid(
                    getAuctionSearchResponseVoList
                        .get(getAuctionSearchResponseVoList.size() - 1)
                        .getCurrentBid())
                .lastAuctionViewCount(
                    getAuctionSearchResponseVoList
                        .get(getAuctionSearchResponseVoList.size() - 1)
                        .getViewCount())
                .build();

    return AuctionSearchPageResponse.builder()
        .getAuctionSearchResponseVoList(getAuctionSearchResponseVoList)
        .nextCursorVo(nextCursorVo)
        .build();
  }

  public AuctionSuggestionResponse toSuggestAuctionSearchResponseVo(
      AuctionSuggestionResult suggestAuctionSearchResponseDto) {
    return AuctionSuggestionResponse.builder()
        .keyword(suggestAuctionSearchResponseDto.getKeyword())
        .weight(suggestAuctionSearchResponseDto.getWeight())
        .build();
  }
}
