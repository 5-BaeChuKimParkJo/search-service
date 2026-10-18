package com.chalnakchalnak.search.adapter.out.http;

import com.chalnakchalnak.search.adapter.out.feign.CategoryClient;
import com.chalnakchalnak.search.adapter.out.feign.TagClient;
import com.chalnakchalnak.search.application.port.out.TaxonomyLookupPort;
import com.chalnakchalnak.search.application.result.CategoryResult;
import com.chalnakchalnak.search.application.result.TagResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaxonomyHttpAdapter implements TaxonomyLookupPort {
  private final CategoryClient categories;
  private final TagClient tags;

  @Override
  public CategoryResult getCategory(int id) {
    return categories.getCategory(id);
  }

  @Override
  public TagResult getTagById(Long id) {
    return tags.getTagById(id);
  }
}
