package org.example.searchservice.application.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.adapter.out.elasticsearch.entity.AuctionSearchDocument;
import org.example.searchservice.adapter.out.elasticsearch.querybuilder.AuctionSearchQueryBuilder;
import org.example.searchservice.application.dto.in.CreateAuctionSearchRequestDto;
import org.example.searchservice.application.dto.in.GetAuctionSearchRequestDto;
import org.example.searchservice.application.dto.out.GetAuctionSearchResponseDto;
import org.example.searchservice.application.dto.out.SuggestAuctionSearchResponseDto;
import org.example.searchservice.application.port.in.AuctionSearchUseCase;
import org.example.searchservice.application.port.out.AuctionSearchRepositoryPort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionSearchService implements AuctionSearchUseCase {

    private final AuctionSearchRepositoryPort auctionSearchRepositoryPort;
    private final AuctionSearchQueryBuilder auctionSearchQueryBuilder;
    private final ElasticsearchClient elasticsearchClient;
    @Override
    public List<GetAuctionSearchResponseDto> searchAuctions(GetAuctionSearchRequestDto getAuctionSearchRequestDto) {

//        NativeQuery query = auctionSearchQueryBuilder.buildQuery(getAuctionSearchRequestDto);
//        log.info("Executing search with query: {}", query);

        return auctionSearchRepositoryPort.search(getAuctionSearchRequestDto);
    }

    @Override
    public void createAuctionSearch(CreateAuctionSearchRequestDto createAuctionSearchRequestDto) {
        auctionSearchRepositoryPort.save(createAuctionSearchRequestDto);
    }

    @Override
    public List<SuggestAuctionSearchResponseDto> suggestAuctions(String keyword) {

        try {
            SearchRequest searchRequest = new SearchRequest.Builder()
                    .index("auction_search")
                    .query(q -> q
                            .match(m -> m
                                    .field("title")
                                    .query(keyword)
                            )
                    )
                    .size(10) // Limit the number of suggestions
                    .build();

            SearchResponse<AuctionSearchDocument> searchResponse = elasticsearchClient.search(
                    searchRequest,
                    AuctionSearchDocument.class
            );

            return searchResponse.hits().hits().stream()
                    .map(hit -> new SuggestAuctionSearchResponseDto(
                            hit.source().getTitle()
                    ))
                    .toList();

        } catch (IOException e) {
            log.error("Error executing search suggestion for keyword: {}", keyword, e);
            throw new RuntimeException("Failed to fetch suggestions", e);

        }
    }



}
