package com.chalnakchalnak.search.adapter.out.elasticsearch.querybuilder;

import static org.assertj.core.api.Assertions.assertThat;

import com.chalnakchalnak.search.application.query.AuctionSearchQuery;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;

class AuctionSearchQueryBuilderTest {

  private final AuctionSearchQueryBuilder queryBuilder = new AuctionSearchQueryBuilder();

  @ParameterizedTest
  @ValueSource(strings = {"latest", "priceHigh", "priceLow", "recommended"})
  void usesMappedAuctionUuidAsTheTieBreaker(String sortBy) {
    AuctionSearchQuery request = AuctionSearchQuery.builder().sortBy(sortBy).build();

    NativeQuery query = queryBuilder.buildAuctionsSearchQuery(request);

    assertThat(query.getSort().getOrderFor("auctionUuid")).isNotNull();
    assertThat(query.getSort().getOrderFor("auctionUuid.keyword")).isNull();
  }
}
