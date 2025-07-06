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
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


@Slf4j
@Repository
@RequiredArgsConstructor
public class AuctionSearchRepository implements AuctionSearchRepositoryPort {

    private final AuctionSearchElasticRepository auctionSearchElasticRepository;
    private final AuctionSearchDocumentMapper auctionSearchDocumentMapper;
    private final ElasticsearchOperations elasticsearchOperations;
    private final AuctionSearchQueryBuilder auctionSearchQueryBuilder;

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
    public void upsertAuctionBulk(List<AuctionUpsertEventDto> auctionUpsertEventDto) {

        List<AuctionSearchDocument> auctionSearchDocuments = auctionUpsertEventDto.stream()
                .map(auctionSearchDocumentMapper::toAuctionSearchDocument)
                .toList();

        try {
            auctionSearchElasticRepository.saveAll(auctionSearchDocuments);
            log.info("Bulk upsert of auctions completed successfully.");
        } catch (Exception e) {
            log.error("Error during bulk upsert of auctions: {}", e.getMessage(), e);
            throw new BaseException(BaseResponseStatus.FAILED_AUCTION_BULK_UPSERT);
        }

    }

    @Override
    public void deleteAuctionBulk(List<AuctionDeleteEventDto> auctionDeleteEventDto) {

        List<String> auctionUuids = auctionDeleteEventDto.stream()
                .map(AuctionDeleteEventDto::getAuctionUuid)
                .toList();

        List<String> auctionIds = new ArrayList<>();

        auctionUuids.stream()
                .forEach(uuid -> {
                    try {
                        auctionSearchElasticRepository.findByAuctionUuid(uuid)
                                .ifPresent(auctionSearchDocument -> {
                                    auctionIds.add(auctionSearchDocument.getId());
                                    log.info("Found auction with UUID: {}", uuid);
                                });
                    } catch (Exception e) {
                        log.error("Error finding auction with UUID {}: {}", uuid, e.getMessage(), e);
                        throw new BaseException(BaseResponseStatus.FAILED_AUCTION_BULK_DELETE);
                    }
                });

        auctionIds.stream()
                .forEach(id -> {
                    try {
                        auctionSearchElasticRepository.deleteById(id);
                    } catch (Exception e) {
                        log.error("Error deleting auction with ID {}: {}", id, e.getMessage(), e);
                        throw new BaseException(BaseResponseStatus.FAILED_AUCTION_BULK_DELETE);
                    }
                });



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
