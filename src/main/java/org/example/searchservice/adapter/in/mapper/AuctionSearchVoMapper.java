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
                .title(createAuctionSearchRequestVo.getTitle())
                .description(createAuctionSearchRequestVo.getDescription())
                .status(createAuctionSearchRequestVo.getStatus())
                .thumbnailKey(createAuctionSearchRequestVo.getThumbnailKey())
                .directDealLocation(createAuctionSearchRequestVo.getDirectDealLocation())
                .build();
    }

    public GetAuctionSearchRequestDto toGetAuctionSearchRequestDto(GetAuctionSearchRequestVo getAuctionSearchRequestVo) {
        return GetAuctionSearchRequestDto.builder()
                .auctionUuid(getAuctionSearchRequestVo.getAuctionUuid())
                .title(getAuctionSearchRequestVo.getTitle())
                .description(getAuctionSearchRequestVo.getDescription())
                .status(getAuctionSearchRequestVo.getStatus())
                .directDealLocation(getAuctionSearchRequestVo.getDirectDealLocation())
                .build();
    }

    public GetAuctionSearchResponseVo toGetAuctionSearchResponseVo(GetAuctionSearchResponseDto getAuctionSearchResponseDto) {
        return GetAuctionSearchResponseVo.builder()
                .auctionUuid(getAuctionSearchResponseDto.getAuctionUuid())
                .title(getAuctionSearchResponseDto.getTitle())
                .description(getAuctionSearchResponseDto.getDescription())
                .status(getAuctionSearchResponseDto.getStatus())
                .thumbnailKey(getAuctionSearchResponseDto.getThumbnailKey())
                .directDealLocation(getAuctionSearchResponseDto.getDirectDealLocation())
                .build();
    }

    public SuggestAuctionSearchResponseVo toSuggestAuctionSearchResponseVo(SuggestAuctionSearchResponseDto suggestAuctionSearchResponseDto) {
        return SuggestAuctionSearchResponseVo.builder()
                .keyword(suggestAuctionSearchResponseDto.getKeyword())
                .build();
    }
}
