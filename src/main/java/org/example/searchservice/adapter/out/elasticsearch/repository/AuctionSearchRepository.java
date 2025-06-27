package org.example.searchservice.adapter.out.elasticsearch.repository;


import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.adapter.out.elasticsearch.entity.AuctionSearchDocument;
import org.example.searchservice.adapter.out.elasticsearch.mapper.AuctionSearchDocumentMapper;
import org.example.searchservice.application.dto.in.*;
import org.example.searchservice.application.dto.out.GetAuctionSearchResponseDto;
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
    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<GetAuctionSearchResponseDto> search(GetAuctionSearchRequestDto getAuctionSearchRequestDto) {

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

        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(boolBuilder.build()._toQuery())
                .build();

        SearchHits<AuctionSearchDocument> hits =
                elasticsearchOperations.search(nativeQuery, AuctionSearchDocument.class);

        log.info("Search hits found: {}", hits.getTotalHits());
        log.info("Search hits content: {}", hits.getSearchHits().stream().map(hit -> hit.getContent().getSellerUuid()).toList());

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



}
