package com.chalnakchalnak.search.adapter.in.web.mapper;

import com.chalnakchalnak.search.adapter.in.web.request.ProductSearchRequest;
import com.chalnakchalnak.search.adapter.in.web.response.*;
import com.chalnakchalnak.search.application.query.ProductSearchQuery;
import com.chalnakchalnak.search.application.result.ProductSearchResult;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
public class ProductSearchWebMapper {

  public ProductSearchQuery toGetProductSearchRequestDto(@ModelAttribute ProductSearchRequest vo) {
    return ProductSearchQuery.builder()
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

  public ProductSearchResponse toGetProductSearchResponseVo(ProductSearchResult responseDto) {
    return ProductSearchResponse.builder()
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

  public ProductSearchPageResponse toGetProductSearchResponseWrapperVo(
      List<ProductSearchResult> responseDtos) {

    List<ProductSearchResponse> responseVos =
        responseDtos.stream().map(this::toGetProductSearchResponseVo).toList();

    ProductCursorResponse nextCursorVo =
        responseDtos.isEmpty()
            ? null
            : ProductCursorResponse.builder()
                .lastProductPrice(responseDtos.get(responseDtos.size() - 1).getPrice())
                .lastProductUuid(responseDtos.get(responseDtos.size() - 1).getProductUuid())
                .lastProductViewCount(responseDtos.get(responseDtos.size() - 1).getViewCount())
                .lastProductCreatedAt(responseDtos.get(responseDtos.size() - 1).getCreatedAt())
                .build();

    return ProductSearchPageResponse.builder()
        .getProductSearchResponseVoList(responseVos)
        .nextCursorVo(nextCursorVo)
        .build();
  }
}
