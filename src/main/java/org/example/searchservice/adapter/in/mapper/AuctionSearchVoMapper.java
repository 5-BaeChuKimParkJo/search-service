package org.example.searchservice.adapter.in.mapper;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.adapter.in.vo.in.CreateAuctionSearchRequestVo;
import org.example.searchservice.adapter.in.vo.in.GetAuctionSearchRequestVo;
import org.example.searchservice.adapter.in.vo.out.GetAuctionSearchResponseVo;
import org.example.searchservice.adapter.in.vo.out.GetAuctionSearchResponseWrapperVo;
import org.example.searchservice.adapter.in.vo.out.NextCursorVo;
import org.example.searchservice.adapter.in.vo.out.SuggestAuctionSearchResponseVo;
import org.example.searchservice.application.dto.in.CreateAuctionSearchRequestDto;
import org.example.searchservice.application.dto.in.GetAuctionSearchRequestDto;
import org.example.searchservice.application.dto.out.GetAuctionSearchResponseDto;
import org.example.searchservice.application.dto.out.SuggestAuctionSearchResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Getter
@NoArgsConstructor
@Component
public class AuctionSearchVoMapper {

    public CreateAuctionSearchRequestDto toCreateAuctionSearchRequestDto(CreateAuctionSearchRequestVo createAuctionSearchRequestVo) {
        return CreateAuctionSearchRequestDto.builder()
                .auctionUuid(createAuctionSearchRequestVo.getAuctionUuid())
                .auctionTitle(createAuctionSearchRequestVo.getAuctionTitle())
                .description(createAuctionSearchRequestVo.getDescription())
                .status(createAuctionSearchRequestVo.getStatus())
                .thumbnailKey(createAuctionSearchRequestVo.getThumbnailKey())
                .directDealLocation(createAuctionSearchRequestVo.getDirectDealLocation())
                .build();
    }

    public GetAuctionSearchRequestDto toGetAuctionSearchRequestDto(GetAuctionSearchRequestVo getAuctionSearchRequestVo) {
        return GetAuctionSearchRequestDto.builder()
                .auctionTitle(getAuctionSearchRequestVo.getAuctionTitle())
                .categoryName(getAuctionSearchRequestVo.getCategoryName())
                .tagNames(getAuctionSearchRequestVo.getTagNames())
                .isDirectDeal(getAuctionSearchRequestVo.isDirectDeal())
                .productCondition(getAuctionSearchRequestVo.getProductCondition())
                .sortBy(getAuctionSearchRequestVo.getSortBy())
                .searchAfter(getAuctionSearchRequestVo.getSearchAfter())
                .build();

    }

    public GetAuctionSearchResponseVo toGetAuctionSearchResponseVo(GetAuctionSearchResponseDto getAuctionSearchResponseDto) {

        return GetAuctionSearchResponseVo.builder()
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

    public GetAuctionSearchResponseWrapperVo toGetAuctionSearchResponseWrapperVo(List<GetAuctionSearchResponseDto> dtoList) {

        List<GetAuctionSearchResponseVo> getAuctionSearchResponseVoList = dtoList.stream()
                .map(this::toGetAuctionSearchResponseVo)
                .toList();

        NextCursorVo nextCursorVo = getAuctionSearchResponseVoList.isEmpty() ? null :
                NextCursorVo.builder()
                        .lastAuctionUuid(getAuctionSearchResponseVoList.get(getAuctionSearchResponseVoList.size() - 1).getAuctionUuid())
                        .lastAuctionCreatedAt(getAuctionSearchResponseVoList.get(getAuctionSearchResponseVoList.size() - 1).getCreatedAt())
                        .lastAuctionCurrentBid(getAuctionSearchResponseVoList.get(getAuctionSearchResponseVoList.size() - 1).getCurrentBid())
                        .lastAuctionViewCount(getAuctionSearchResponseVoList.get(getAuctionSearchResponseVoList.size() - 1).getViewCount())
                        .build();

        return GetAuctionSearchResponseWrapperVo.builder()
                .getAuctionSearchResponseVoList(getAuctionSearchResponseVoList)
                .nextCursorVo(nextCursorVo)
                .build();
    }

    public SuggestAuctionSearchResponseVo toSuggestAuctionSearchResponseVo(SuggestAuctionSearchResponseDto suggestAuctionSearchResponseDto) {
        return SuggestAuctionSearchResponseVo.builder()
                .keyword(suggestAuctionSearchResponseDto.getKeyword())
                .weight(suggestAuctionSearchResponseDto.getWeight())
                .build();
    }
}
