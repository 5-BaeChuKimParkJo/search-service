package org.example.searchservice.adapter.out.elasticsearch.querybuilder;

import org.example.searchservice.application.dto.in.GetAuctionSearchRequestDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;

import static org.assertj.core.api.Assertions.assertThat;

class AuctionSearchQueryBuilderTest {

    private final AuctionSearchQueryBuilder queryBuilder = new AuctionSearchQueryBuilder();

    @ParameterizedTest
    @ValueSource(strings = {"latest", "priceHigh", "priceLow", "recommended"})
    void usesMappedAuctionUuidAsTheTieBreaker(String sortBy) {
        GetAuctionSearchRequestDto request = GetAuctionSearchRequestDto.builder()
                .sortBy(sortBy)
                .build();

        NativeQuery query = queryBuilder.buildAuctionsSearchQuery(request);

        assertThat(query.getSort().getOrderFor("auctionUuid")).isNotNull();
        assertThat(query.getSort().getOrderFor("auctionUuid.keyword")).isNull();
    }
}
