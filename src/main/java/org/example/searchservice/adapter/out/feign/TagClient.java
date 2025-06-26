package org.example.searchservice.adapter.out.feign;


import org.example.searchservice.application.dto.in.TagResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tag-service", url = "https://api.cabbage-secondhand.shop/tag-service/api/v1/")
@Qualifier("tagClient")
public interface TagClient {

    @GetMapping("/tag/name/{name}")
    TagResponseDto getTagByName(@PathVariable String name);

    @GetMapping("/tag/{tagId}")
    TagResponseDto getTagById(@PathVariable Long tagId);
}
