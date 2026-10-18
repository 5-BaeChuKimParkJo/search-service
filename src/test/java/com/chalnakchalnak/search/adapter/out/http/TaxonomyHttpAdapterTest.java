package com.chalnakchalnak.search.adapter.out.http;

import static org.mockito.Mockito.*;

import com.chalnakchalnak.search.adapter.out.feign.CategoryClient;
import com.chalnakchalnak.search.adapter.out.feign.TagClient;
import org.junit.jupiter.api.Test;

class TaxonomyHttpAdapterTest {
  @Test
  void forwardsLookupThroughTheExistingClients() {
    var categories = mock(CategoryClient.class);
    var tags = mock(TagClient.class);
    var adapter = new TaxonomyHttpAdapter(categories, tags);
    adapter.getCategory(5);
    adapter.getTagById(8L);
    verify(categories).getCategory(5);
    verify(tags).getTagById(8L);
  }
}
