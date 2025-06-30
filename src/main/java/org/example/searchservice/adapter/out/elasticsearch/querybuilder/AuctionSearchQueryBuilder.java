package org.example.searchservice.adapter.out.elasticsearch.querybuilder;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.application.dto.in.GetAuctionSearchRequestDto;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Sort;


@Slf4j
@Component
public class AuctionSearchQueryBuilder {

    public NativeQuery buildAuctionsSearchQuery(GetAuctionSearchRequestDto getAuctionSearchRequestDto) {

        BoolQuery.Builder boolBuilder = QueryBuilders.bool();

        if (getAuctionSearchRequestDto.getAuctionTitle() != null && !getAuctionSearchRequestDto.getAuctionTitle().isEmpty()) {
            boolBuilder.must(q -> q.match(m -> m
                    .field(("auctionTitle"))
                    .query(getAuctionSearchRequestDto.getAuctionTitle()
                    )));
        }

        if (getAuctionSearchRequestDto.getCategoryName() != null && !getAuctionSearchRequestDto.getCategoryName().isEmpty()) {
            boolBuilder.filter(q -> q.term(t -> t
                    .field("categoryName")
                    .value(getAuctionSearchRequestDto.getCategoryName())));
        }

        if (getAuctionSearchRequestDto.getTagNames() != null && !getAuctionSearchRequestDto.getTagNames().isEmpty()) {
            boolBuilder.filter(q -> q.terms(t -> t
                    .field("tagNames")
                    .terms(v -> v.value(
                            getAuctionSearchRequestDto.getTagNames().stream()
                                    .map(FieldValue::of)
                                    .toList()
                    ))
            ));
        }

        if (getAuctionSearchRequestDto.isDirectDeal()) {
            boolBuilder.filter(q -> q.term(t -> t
                    .field("isDirectDeal")
                    .value(getAuctionSearchRequestDto.isDirectDeal())));
        }

        if (getAuctionSearchRequestDto.getProductCondition() != null && !getAuctionSearchRequestDto.getProductCondition().isEmpty()) {
            boolBuilder.filter(q -> q.term(t -> t
                    .field("productCondition")
                    .value(getAuctionSearchRequestDto.getProductCondition())));
        }

        // Sorting logic
        Sort sort = Sort.unsorted();
        List<Object> searchAfter = getAuctionSearchRequestDto.getSearchAfter();
        log.info("Search after: {}", searchAfter);

        String sortBy = getAuctionSearchRequestDto.getSortBy();
        log.info("Sort by: {}", sortBy);

        if (sortBy != null) {
            switch (sortBy.trim()) {
                case "latest": // 최신순
                    sort = Sort.by(
                            Sort.Order.desc("createdAt"),
                            Sort.Order.asc("auctionUuid")
                    );
                    break;
                case "priceHigh": // 높은 가격순
                    sort = Sort.by(
                            Sort.Order.desc("currentBid"),
                            Sort.Order.asc("auctionUuid")
                    );
                    break;
                case "priceLow": // 낮은 가격순
                    sort = Sort.by(
                            Sort.Order.asc("currentBid"),
                            Sort.Order.asc("auctionUuid")
                    );

                    break;
                case "recommended": // 추천순 (정렬 기준 추가 필요)
                    sort = Sort.by(
                            Sort.Order.desc("viewCount"),
                            Sort.Order.asc("auctionUuid")
                    );
                    break;
                default:
                    break;
            }

            log.info("Sort: {}", sort);

            return NativeQuery.builder()
                    .withQuery(boolBuilder.build()._toQuery())
                    .withSort(sort)
                    .withSearchAfter(searchAfter)
                    .build();
        }

        else {

            log.info("Sort: {}", sort);

            return NativeQuery.builder()
                    .withQuery(boolBuilder.build()._toQuery())
                    .build();
        }


    }

    public NativeQuery buildAuctionsSuggestQuery(String keyword) {

        BoolQuery.Builder boolBuilder = QueryBuilders.bool();

        if (keyword != null && !keyword.isEmpty()) {
            boolBuilder.must(q -> q.match(m -> m
                    .field("tagNames")
                    .query(keyword)
            ));
        }

        return NativeQuery.builder()
                .withQuery(boolBuilder.build()._toQuery())
                .withMaxResults(5)
                .build();
    }



}
