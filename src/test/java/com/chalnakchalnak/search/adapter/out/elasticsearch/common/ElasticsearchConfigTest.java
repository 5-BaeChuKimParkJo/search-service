package com.chalnakchalnak.search.adapter.out.elasticsearch.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ElasticsearchConfigTest {

  @Test
  void convertsHttpUrlToSpringDataConnectionAddress() {
    assertEquals(
        "100.84.9.65:19200", ElasticsearchConfig.connectionAddress("http://100.84.9.65:19200"));
  }
}
