package org.example.searchservice.adapter.out.elasticsearch.repository;


import co.elastic.clients.elasticsearch._types.ElasticsearchException;
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
import org.example.searchservice.common.exception.BaseException;
import org.example.searchservice.common.response.BaseResponseStatus;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
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
            log.info("Search Sort {}", nativeQuery.getSort());
            log.info("Search After {}", nativeQuery.getSearchAfter());

            try {
                SearchHits<AuctionSearchDocument> hits =
                        elasticsearchOperations.search(nativeQuery, AuctionSearchDocument.class);

                return hits.getSearchHits().stream()
                        .map(hit -> auctionSearchDocumentMapper.toGetAuctionSearchResponseDto(hit.getContent()))
                        .toList();

            } catch (ElasticsearchException | UncategorizedElasticsearchException e) {
                    log.error("Elasticsearch error occurred: {}", e.getMessage(), e);
                    throw new BaseException(BaseResponseStatus.FAILED_AUCTION_SEARCH);
                }


    }

    @Override
    public void save(CreateAuctionSearchRequestDto createAuctionSearchRequestDto) {
        try {
            auctionSearchElasticRepository.save(
                    auctionSearchDocumentMapper.toAuctionSearchDocument(createAuctionSearchRequestDto)
            );
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_AUCTION_SAVE);
        }
    }

    @Override
    public void saveAuction(AuctionCreateEventDto auctionCreateEventDto, CategoryResponseDto categoryResponseDto, List<TagResponseDto> tagResponseDtoList) {

//        auctionSearchElasticRepository.findByAuctionUuid(auctionCreateEventDto.getAuctionUuid())
//                .ifPresent(existingDocument -> {
//                    log.info("Auction with UUID {} already exists, skipping save.", auctionCreateEventDto.getAuctionUuid());
//                    throw new BaseException(BaseResponseStatus.AUCTION_ALREADY_EXISTS);
//                });

        try {
            auctionSearchElasticRepository.save(
                    auctionSearchDocumentMapper.toAuctionSearchDocument(
                            auctionCreateEventDto,
                            categoryResponseDto,
                            tagResponseDtoList
                    )
            );
            log.info("Auction with UUID {} saved successfully.", auctionCreateEventDto.getAuctionUuid());
            log.info("Category: {}", auctionCreateEventDto.getTitle());
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_AUCTION_SAVE);
        }
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
