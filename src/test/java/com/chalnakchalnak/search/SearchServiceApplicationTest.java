package com.chalnakchalnak.search;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    properties = {
      "spring.elasticsearch.uris=http://127.0.0.1:19299",
      "spring.elasticsearch.username=test",
      "spring.elasticsearch.password=test"
    })
class SearchServiceApplicationTest {
  @org.springframework.test.context.bean.override.mockito.MockitoBean
  private com.chalnakchalnak.search.adapter.out.elasticsearch.repository
          .AuctionElasticsearchRepository
      auctions;

  @org.springframework.test.context.bean.override.mockito.MockitoBean
  private com.chalnakchalnak.search.adapter.out.elasticsearch.repository
          .ProductElasticsearchRepository
      products;

  @org.springframework.test.context.bean.override.mockito.MockitoBean
  private com.chalnakchalnak.search.adapter.out.elasticsearch.repository
          .KeywordElasticsearchRepository
      keywords;

  @org.springframework.test.context.bean.override.mockito.MockitoBean
  private org.springframework.kafka.config.KafkaListenerEndpointRegistry kafkaListeners;

  @Test
  void contextLoads() {}
}
