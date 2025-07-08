package org.example.searchservice.adapter.in.mapper;


import org.example.searchservice.adapter.in.vo.in.GetProductSearchRequestVo;
import org.example.searchservice.adapter.in.vo.out.*;
import org.example.searchservice.application.dto.in.GetProductSearchRequestDto;
import org.example.searchservice.application.dto.out.GetProductSearchResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Component
public class ProductSearchVoMapper {

    public GetProductSearchRequestDto toGetProductSearchRequestDto(
            @ModelAttribute GetProductSearchRequestVo vo
    ) {
        return GetProductSearchRequestDto.builder()
                .productTitle(vo.getProductTitle())
                .categoryName(vo.getCategoryName())
                .tagNames(vo.getTagNames())
                .isDirectDeal(vo.isDirectDeal())
                .directDealLocation(vo.getDirectDealLocation())
                .productCondition(vo.getProductCondition())
                .sortBy(vo.getSortBy())
                .searchAfter1(vo.getSearchAfter1())
                .searchAfter2(vo.getSearchAfter2())
                .build();
    }

    public GetProductSearchResponseVo toGetProductSearchResponseVo(
            GetProductSearchResponseDto responseDto
    ) {
        return GetProductSearchResponseVo.builder()
                .id(responseDto.getId())
                .price(responseDto.getPrice())
                .productUuid(responseDto.getProductUuid())
                .saleMemberUuid(responseDto.getSaleMemberUuid())
                .title(responseDto.getTitle())
                .categoryId(responseDto.getCategoryId())
                .description(responseDto.getDescription())
                .productCondition(responseDto.getProductCondition())
                .isDirectDeal(responseDto.isDirectDeal())
                .directDealLocation(responseDto.getDirectDealLocation())
                .isHide(responseDto.getIsHide())
                .status(responseDto.getStatus())
                .thumbnailKey(responseDto.getThumbnailKey())
                .viewCount(responseDto.getViewCount())
                .ticketUuid(responseDto.getTicketUuid())
                .imageUrlList(responseDto.getImageUrlList())
                .tagIdList(responseDto.getTagIdList())
                .isDeleted(responseDto.getIsDeleted())
                .createdAt(responseDto.getCreatedAt())
                .tagNames(responseDto.getTagNames())
                .categoryName(responseDto.getCategoryName())
                .build();
    }


    public GetProductSearchResponseWrapperVo toGetProductSearchResponseWrapperVo(
            List<GetProductSearchResponseDto> responseDtos
    ) {

        List<GetProductSearchResponseVo> responseVos = responseDtos.stream()
                .map(this::toGetProductSearchResponseVo)
                .toList();

        NextCursorProductVo nextCursorVo = NextCursorProductVo.builder()
                .lastProductPrice(responseDtos.isEmpty() ? null : responseDtos.get(responseDtos.size() - 1).getPrice())
                .lastProductUuid(responseDtos.isEmpty() ? null : responseDtos.get(responseDtos.size() - 1).getProductUuid())
                .lastProductCreatedAt(responseDtos.isEmpty() ? null : responseDtos.get(responseDtos.size() - 1).getCreatedAt())
                .lastProductViewCount(responseDtos.isEmpty() ? null : responseDtos.get(responseDtos.size() - 1).getViewCount())
                .build();


        return GetProductSearchResponseWrapperVo.builder()
                .getProductSearchResponseVoList(responseVos)
                .nextCursorVo(nextCursorVo)
                .build();
    }

}
