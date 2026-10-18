package com.chalnakchalnak.search.application.port.out;

import com.chalnakchalnak.search.application.result.CategoryResult;
import com.chalnakchalnak.search.application.result.TagResult;

public interface TaxonomyLookupPort {
  CategoryResult getCategory(int categoryId);

  TagResult getTagById(Long tagId);
}
