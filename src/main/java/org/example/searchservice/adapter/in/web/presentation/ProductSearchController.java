package org.example.searchservice.adapter.in.web.presentation;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.adapter.in.mapper.ProductSearchVoMapper;
import org.example.searchservice.adapter.in.vo.in.GetProductSearchRequestVo;
import org.example.searchservice.adapter.in.vo.out.GetProductSearchResponseVo;
import org.example.searchservice.adapter.in.vo.out.GetProductSearchResponseWrapperVo;
import org.example.searchservice.application.dto.out.GetProductSearchResponseDto;
import org.example.searchservice.application.port.in.ProductSearchUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product/search")
public class ProductSearchController {

    private final ProductSearchUseCase productSearchUseCase;
    private final ProductSearchVoMapper productSearchVoMapper;


    @Operation(summary = "Search for products based on various criteria: sortBy: latest, priceHigh, priceLow, recommended")
    @GetMapping
    public GetProductSearchResponseWrapperVo getProductSearchList(
            @ModelAttribute GetProductSearchRequestVo getProductSearchRequestVo
    ) {

        log.info("Received request to search products with parameters: {}", getProductSearchRequestVo.getProductTitle());

        List<GetProductSearchResponseDto> getProductSearchResponseDtoList = productSearchUseCase.searchProduct(
                productSearchVoMapper.toGetProductSearchRequestDto(getProductSearchRequestVo)
        );

        return productSearchVoMapper.toGetProductSearchResponseWrapperVo(
                getProductSearchResponseDtoList
        );

    }


}
