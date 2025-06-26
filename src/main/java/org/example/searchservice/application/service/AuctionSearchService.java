package org.example.searchservice.application.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.adapter.in.kafka.event.AuctionCreateEvent;
import org.example.searchservice.adapter.out.elasticsearch.entity.AuctionSearchDocument;
import org.example.searchservice.adapter.out.elasticsearch.querybuilder.AuctionSearchQueryBuilder;
import org.example.searchservice.adapter.out.feign.CategoryClient;
import org.example.searchservice.adapter.out.feign.TagClient;
import org.example.searchservice.application.dto.in.*;
import org.example.searchservice.application.dto.out.GetAuctionSearchResponseDto;
import org.example.searchservice.application.dto.out.SuggestAuctionSearchResponseDto;
import org.example.searchservice.application.port.in.AuctionSearchUseCase;
import org.example.searchservice.application.port.out.AuctionSearchRepositoryPort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionSearchService implements AuctionSearchUseCase {

    private final AuctionSearchRepositoryPort auctionSearchRepositoryPort;
    private final AuctionSearchQueryBuilder auctionSearchQueryBuilder;
    private final ElasticsearchClient elasticsearchClient;
    private final CategoryClient categoryClient;
    private final TagClient tagClient;

    @Override
    public List<GetAuctionSearchResponseDto> searchAuctions(GetAuctionSearchRequestDto getAuctionSearchRequestDto) {

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
                                    .field("auctionTitle")
                                    .query(keyword)
                            )
                    )
                    .size(10) // Limit the number of suggestions
                    .build();

            SearchResponse<AuctionSearchDocument> searchResponse = elasticsearchClient.search(
                    searchRequest,
                    AuctionSearchDocument.class
            );

            log.info("Search suggestions for keyword '{}': {}", keyword, searchResponse.hits().hits().size());

            return searchResponse.hits().hits().stream()
                    .map(hit -> new SuggestAuctionSearchResponseDto(
                            hit.source().getAuctionTitle()
                    ))
                    .toList();

        } catch (IOException e) {
            log.error("Error executing search suggestion for keyword: {}", keyword, e);
            throw new RuntimeException("Failed to fetch suggestions", e);

        }
    }

    @Override
    public void saveAuction(AuctionCreateEventDto auctionCreateEventDto) {

        CategoryResponseDto categoryResponseDto = categoryClient.getCategory(auctionCreateEventDto.getCategoryId());

        List<TagResponseDto> tagResponseDtoList = new ArrayList<>();

        auctionCreateEventDto.getTagIds().stream()
                .forEach( tagId -> {
                    try {
                        TagResponseDto tagResponseDto = tagClient.getTagById(tagId);
                        System.out.println("Tag Response: " + tagResponseDto.getTagName());
                        tagResponseDtoList.add(tagResponseDto);
                    } catch (Exception e) {
                        log.error("Error fetching tag with ID {}: {}", tagId, e.getMessage());
                    }
                });

        System.out.println("Category Response: " + categoryResponseDto.getCategoryName());

        System.out.println("auctionCreateEventDto = " + auctionCreateEventDto.getAuctionUuid());

        auctionSearchRepositoryPort.saveAuction(auctionCreateEventDto, categoryResponseDto, tagResponseDtoList);

    }


}
