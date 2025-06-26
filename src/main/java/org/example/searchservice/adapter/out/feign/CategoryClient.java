package org.example.searchservice.adapter.out.feign;


import feign.Response;
import lombok.RequiredArgsConstructor;
import org.example.searchservice.application.dto.in.CategoryResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;

@FeignClient(name = "category-service", url = "https://api.cabbage-secondhand.shop/category-service/api/v1/")
@Qualifier("categoryClient")
public interface CategoryClient {
    @GetMapping("category/list")
    List<CategoryResponseDto> getCategoryList();

    @GetMapping("category/{categoryId}")
    CategoryResponseDto getCategory(@PathVariable("categoryId") int categoryId);
}
