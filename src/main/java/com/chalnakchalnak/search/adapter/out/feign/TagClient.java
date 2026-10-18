package com.chalnakchalnak.search.adapter.out.feign;

import com.chalnakchalnak.search.application.result.TagResult;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tag-service", url = "https://api.cabbage-secondhand.shop/tag-service/api/v1/")
@Qualifier("tagClient")
public interface TagClient {

  @GetMapping("/tag/name/{name}")
  TagResult getTagByName(@PathVariable String name);

  @GetMapping("/tag/{tagId}")
  TagResult getTagById(@PathVariable Long tagId);
}
