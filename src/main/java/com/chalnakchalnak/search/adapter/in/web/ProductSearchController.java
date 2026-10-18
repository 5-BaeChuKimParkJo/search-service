package com.chalnakchalnak.search.adapter.in.web;

import com.chalnakchalnak.search.adapter.in.web.mapper.ProductSearchWebMapper;
import com.chalnakchalnak.search.adapter.in.web.request.ProductSearchRequest;
import com.chalnakchalnak.search.adapter.in.web.response.ProductSearchPageResponse;
import com.chalnakchalnak.search.application.port.in.ProductSearchUseCase;
import com.chalnakchalnak.search.application.result.ProductSearchResult;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/search-service/api/v1/product/search")
public class ProductSearchController {

  private final ProductSearchUseCase productSearchUseCase;
  private final ProductSearchWebMapper productSearchVoMapper;

  @Operation(
      summary =
          "Search for products based on various criteria: sortBy: latest, priceHigh, priceLow, recommended")
  @GetMapping
  public ProductSearchPageResponse getProductSearchList(
      @ModelAttribute ProductSearchRequest getProductSearchRequestVo) {

    log.info(
        "Received request to search products with parameters: {}",
        getProductSearchRequestVo.getProductTitle());

    List<ProductSearchResult> getProductSearchResponseDtoList =
        productSearchUseCase.searchProduct(
            productSearchVoMapper.toGetProductSearchRequestDto(getProductSearchRequestVo));

    return productSearchVoMapper.toGetProductSearchResponseWrapperVo(
        getProductSearchResponseDtoList);
  }
}
