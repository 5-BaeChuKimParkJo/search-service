package com.chalnakchalnak.search.adapter.in.web.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductSearchPageResponse {

  List<ProductSearchResponse> getProductSearchResponseVoList;
  ProductCursorResponse nextCursorVo;

  @Builder
  public ProductSearchPageResponse(
      List<ProductSearchResponse> getProductSearchResponseVoList,
      ProductCursorResponse nextCursorVo) {
    this.getProductSearchResponseVoList = getProductSearchResponseVoList;
    this.nextCursorVo = nextCursorVo;
  }
}
