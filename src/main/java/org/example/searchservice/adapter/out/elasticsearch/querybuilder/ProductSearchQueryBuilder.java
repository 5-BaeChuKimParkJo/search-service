package org.example.searchservice.adapter.out.elasticsearch.querybuilder;


import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.application.dto.in.GetProductSearchRequestDto;
import org.example.searchservice.common.exception.BaseException;
import org.example.searchservice.common.response.BaseResponseStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProductSearchQueryBuilder {

    public NativeQuery buildProductSearchQuery(GetProductSearchRequestDto dto) {

        try {

            if (dto.getSortBy() == null) {
                throw new BaseException(BaseResponseStatus.REQUIRED_SORT_BY);
            }

            BoolQuery.Builder boolBuilder = QueryBuilders.bool();

            if (dto.getProductTitle() != null && !dto.getProductTitle().isEmpty()) {
                boolBuilder.must(q -> q.match(m -> m
                        .field(("title"))
                        .query(dto.getProductTitle()
                        )));
            }

            if (dto.getCategoryName() != null && !dto.getCategoryName().isEmpty()) {
                boolBuilder.filter(q -> q.term(t -> t
                        .field("categoryName")
                        .value(dto.getCategoryName())));
            }

            if (dto.getTagNames() != null && !dto.getTagNames().isEmpty()) {
                boolBuilder.filter(q -> q.terms(t -> t
                        .field("tagNames")
                        .terms(v -> v.value(
                                dto.getTagNames().stream()
                                        .map(FieldValue::of)
                                        .toList()
                        ))
                ));
            }

            if (dto.isDirectDeal()) {
                boolBuilder.filter(q -> q.term(t -> t
                        .field("isDirectDeal")
                        .value(dto.isDirectDeal())));
            }

            if (dto.getDirectDealLocation() != null && !dto.getDirectDealLocation().isEmpty()) {
                boolBuilder.filter(q -> q.term(t -> t
                        .field("directDealLocation")
                        .value(dto.getDirectDealLocation())));
            }

            if (dto.getProductCondition() != null && !dto.getProductCondition().isEmpty()) {
                boolBuilder.filter(q -> q.term(t -> t
                        .field("productCondition")
                        .value(dto.getProductCondition())));
            }

            Sort sort = Sort.unsorted();
            String searchAfter1 = dto.getSearchAfter1();
            String searchAfter2 = dto.getSearchAfter2();

            List<Object> searchAfter = List.of(
                    searchAfter1 != null ? searchAfter1 : "",
                    searchAfter2 != null ? searchAfter2 : ""
            );
            log.info("searchAfter: {}", searchAfter);

            String sortBy = dto.getSortBy();

            switch (sortBy.trim()) {
                case "latest": // 최신순
                    sort = Sort.by(
                            Sort.Order.desc("createdAt"),
                            Sort.Order.asc("productUuid.keyword")
                    );
                    break;
                case "priceHigh": // 높은 가격순
                    sort = Sort.by(
                            Sort.Order.desc("price"),
                            Sort.Order.asc("productUuid.keyword")
                    );
                    break;
                case "priceLow": // 낮은 가격순
                    sort = Sort.by(
                            Sort.Order.asc("price"),
                            Sort.Order.asc("productUuid.keyword")
                    );
                    break;
                case "recommended": // 추천순 (정렬 기준 추가 필요)
                    sort = Sort.by(
                            Sort.Order.desc("viewCount"),
                            Sort.Order.asc("productUuid.keyword")
                    );
                    break;
                default:
                    break;
            }

            if (searchAfter.get(0) == "" || searchAfter.isEmpty()) {
                return NativeQuery.builder()
                        .withQuery(boolBuilder.build()._toQuery())
                        .withSort(sort)
                        .withMaxResults(20)
                        .build();
            }

            return NativeQuery.builder()
                    .withQuery(boolBuilder.build()._toQuery())
                    .withSort(sort)
                    .withSearchAfter(searchAfter)
                    .withMaxResults(20)
                    .build();


        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_QUERY_BUILD);
        }

    }

}
