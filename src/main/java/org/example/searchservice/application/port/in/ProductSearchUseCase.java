package org.example.searchservice.application.port.in;

import org.example.searchservice.adapter.in.kafka.event.product.ProductEvent;
import org.example.searchservice.application.dto.in.GetProductSearchRequestDto;
import org.example.searchservice.application.dto.out.GetProductSearchResponseDto;

import java.util.List;

public interface ProductSearchUseCase {

    List<GetProductSearchResponseDto> searchProduct(GetProductSearchRequestDto getProductSearchRequestDto);
    void saveProductBulk(List<ProductEvent> productEvents);
}
