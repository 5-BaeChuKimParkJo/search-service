package org.example.searchservice.adapter.out.elasticsearch.querybuilder;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import org.example.searchservice.application.dto.in.GetAuctionSearchRequestDto;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class AuctionSearchQueryBuilder {

//    public static NativeQuery buildQuery(GetAuctionSearchRequestDto getAuctionSearchRequestDto) {
//
//        BoolQueryBuilder boolQuery = BoolQuery.of(b -> b);
//
//        if (getAuctionSearchRequestDto.getAuctionUuid() != null) {
//            boolQuery.must(QueryBuilders.term(t -> t.field("auctionUuid").value(getAuctionSearchRequestDto.getAuctionUuid())));
//        }
//
//        if (getAuctionSearchRequestDto.getTitle() != null) {
//            boolQuery.must(QueryBuilders.match(m -> m.field("title").query(getAuctionSearchRequestDto.getTitle())));
//        }
//
//        if (getAuctionSearchRequestDto.getDescription() != null) {
//            boolQuery.must(QueryBuilders.match(m -> m.field("description").query(getAuctionSearchRequestDto.getDescription())));
//        }
//
//        if (getAuctionSearchRequestDto.getStatus() != null) {
//            boolQuery.must(QueryBuilders.term(t -> t.field("status").value(getAuctionSearchRequestDto.getStatus())));
//        }
//
//        if (getAuctionSearchRequestDto.getDirectDealLocation() != null) {
//            boolQuery.must(QueryBuilders.match(m -> m.field("directDealLocation").query(getAuctionSearchRequestDto.getDirectDealLocation())));
//        }
//
//        Query finalQuery = Query.of(q -> q.bool(boolQuery.build()));
//
//        return NativeQuery.builder()
//                .withQuery(finalQuery)
//                .build();
//
//    }

}
