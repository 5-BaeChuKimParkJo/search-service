package org.example.searchservice.adapter.in.mapper;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.searchservice.adapter.in.vo.in.CreateAuctionSearchRequestVo;
import org.example.searchservice.adapter.in.vo.in.GetAuctionSearchRequestVo;
import org.example.searchservice.adapter.in.vo.out.GetAuctionSearchResponseVo;
import org.example.searchservice.adapter.in.vo.out.SuggestAuctionSearchResponseVo;
import org.example.searchservice.application.dto.in.CreateAuctionSearchRequestDto;
import org.example.searchservice.application.dto.in.GetAuctionSearchRequestDto;
import org.example.searchservice.application.dto.out.GetAuctionSearchResponseDto;
import org.example.searchservice.application.dto.out.SuggestAuctionSearchResponseDto;
import org.springframework.stereotype.Component;

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

    public SuggestAuctionSearchResponseVo toSuggestAuctionSearchResponseVo(SuggestAuctionSearchResponseDto suggestAuctionSearchResponseDto) {
        return SuggestAuctionSearchResponseVo.builder()
                .keyword(suggestAuctionSearchResponseDto.getKeyword())
                .build();
    }
}
