package org.example.searchservice.adapter.out.elasticsearch.repository;


import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.adapter.out.elasticsearch.entity.AuctionSearchDocument;
import org.example.searchservice.adapter.out.elasticsearch.mapper.AuctionSearchDocumentMapper;
import org.example.searchservice.adapter.out.elasticsearch.querybuilder.AuctionSearchQueryBuilder;
import org.example.searchservice.application.dto.in.CreateAuctionSearchRequestDto;
import org.example.searchservice.application.dto.in.GetAuctionSearchRequestDto;
import org.example.searchservice.application.dto.out.GetAuctionSearchResponseDto;
import org.example.searchservice.application.port.out.AuctionSearchRepositoryPort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Slf4j
@Repository
@RequiredArgsConstructor
public class AuctionSearchRepository implements AuctionSearchRepositoryPort {

    private final AuctionSearchElasticRepository auctionSearchElasticRepository;
    private final AuctionSearchDocumentMapper auctionSearchDocumentMapper;
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<GetAuctionSearchResponseDto> search(GetAuctionSearchRequestDto getAuctionSearchRequestDto) {
        List<AuctionSearchDocument> auctionSearchDocuments = auctionSearchElasticRepository.findByTitle(
                getAuctionSearchRequestDto.getTitle()
        );
        log.info("Found {} auction search documents for title: {}",
                auctionSearchDocuments.size(),
                getAuctionSearchRequestDto.getTitle()
        );

//        Query query = AuctionSearchQueryBuilder.buildQuery(getAuctionSearchRequestDto);

//        SearchHits<AuctionSearchDocument> searchHits = elasticsearchOperations.search(query, AuctionSearchDocument.class);
//        log.info("Search hits: {}", searchHits.getTotalHits());

        return auctionSearchDocuments.stream()
                .map(auctionSearchDocumentMapper::toGetAuctionSearchResponseDto).toList();
    }

    @Override
    public void save(CreateAuctionSearchRequestDto createAuctionSearchRequestDto) {
        auctionSearchElasticRepository.save(
                auctionSearchDocumentMapper.toAuctionSearchDocument(createAuctionSearchRequestDto)
        );
    }
}
