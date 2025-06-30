package org.example.searchservice.adapter.out.elasticsearch.repository;


import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.adapter.out.elasticsearch.entity.AuctionSearchDocument;
import org.example.searchservice.adapter.out.elasticsearch.mapper.AuctionSearchDocumentMapper;
import org.example.searchservice.adapter.out.elasticsearch.mapper.KeywordSearchDocumentMapper;
import org.example.searchservice.adapter.out.elasticsearch.querybuilder.AuctionSearchQueryBuilder;
import org.example.searchservice.application.dto.in.*;
import org.example.searchservice.application.dto.out.GetAuctionSearchResponseDto;
import org.example.searchservice.application.dto.out.SuggestAuctionSearchResponseDto;
import org.example.searchservice.application.port.out.AuctionSearchRepositoryPort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import java.util.List;


@Slf4j
@Repository
@RequiredArgsConstructor
public class AuctionSearchRepository implements AuctionSearchRepositoryPort {

    private final AuctionSearchElasticRepository auctionSearchElasticRepository;
    private final AuctionSearchDocumentMapper auctionSearchDocumentMapper;
    private final ElasticsearchOperations elasticsearchOperations;
    private final AuctionSearchQueryBuilder auctionSearchQueryBuilder;
    private final KeywordSearchDocumentMapper keywordSearchDocumentMapper;

    @Override
    public List<GetAuctionSearchResponseDto> search(GetAuctionSearchRequestDto getAuctionSearchRequestDto) {

        NativeQuery nativeQuery = auctionSearchQueryBuilder.buildAuctionsSearchQuery(getAuctionSearchRequestDto);

        log.info("Executing search with query: {}", nativeQuery.getQuery());
        log.info("Sort: {}", nativeQuery.getSort());
        log.info("SearchAfter: {}", nativeQuery.getSearchAfter());

        SearchHits<AuctionSearchDocument> hits =
                elasticsearchOperations.search(nativeQuery, AuctionSearchDocument.class);

        log.info("Search hits found: {}", hits.getTotalHits());

        log.info("Search hits content: {}", hits.getSearchHits().stream()
                .map(hit -> hit.getContent().getAuctionTitle())
                .toList());

        return hits.getSearchHits().stream()
                .map(hit -> auctionSearchDocumentMapper.toGetAuctionSearchResponseDto(hit.getContent()))
                .toList();

    }

    @Override
    public void save(CreateAuctionSearchRequestDto createAuctionSearchRequestDto) {
        auctionSearchElasticRepository.save(
                auctionSearchDocumentMapper.toAuctionSearchDocument(createAuctionSearchRequestDto)
        );
    }

    @Override
    public void saveMessage(String message) {
        // This method is not implemented in the original code, but can be used for logging or other purposes
        log.info("Saving message: {}", message);

    }

    @Override
    public void saveAuction(AuctionCreateEventDto auctionCreateEventDto, CategoryResponseDto categoryResponseDto, List<TagResponseDto> tagResponseDtoList) {
        auctionSearchElasticRepository.save(
                auctionSearchDocumentMapper.toAuctionSearchDocument(
                        auctionCreateEventDto,
                        categoryResponseDto,
                        tagResponseDtoList
                )
        );
    }

    @Override
    public List<SuggestAuctionSearchResponseDto> suggest(String keyword) {

        NativeQuery nativeQuery = auctionSearchQueryBuilder.buildAuctionsSuggestQuery(keyword);

        SearchHits<AuctionSearchDocument> hits =
                elasticsearchOperations.search(nativeQuery, AuctionSearchDocument.class);

        return hits.getSearchHits().stream()
                .map(hit -> auctionSearchDocumentMapper.toSuggestAuctionSearchResponseDto(hit.getContent().getAuctionTitle()))
                .toList();
    }


}
