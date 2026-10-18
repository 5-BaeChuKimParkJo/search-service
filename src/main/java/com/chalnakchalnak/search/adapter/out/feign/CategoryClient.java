package com.chalnakchalnak.search.adapter.out.feign;

import com.chalnakchalnak.search.application.result.CategoryResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "category-service",
    url = "https://api.cabbage-secondhand.shop/category-service/api/v1/")
@Qualifier("categoryClient")
public interface CategoryClient {
  @GetMapping("category/list")
  List<CategoryResult> getCategoryList();

  @GetMapping("category/{categoryId}")
  CategoryResult getCategory(@PathVariable("categoryId") int categoryId);
}
