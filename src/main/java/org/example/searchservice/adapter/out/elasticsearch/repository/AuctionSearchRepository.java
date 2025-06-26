package org.example.searchservice.adapter.out.elasticsearch.repository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.adapter.out.elasticsearch.entity.AuctionSearchDocument;
import org.example.searchservice.adapter.out.elasticsearch.mapper.AuctionSearchDocumentMapper;
import org.example.searchservice.application.dto.in.*;
import org.example.searchservice.application.dto.out.GetAuctionSearchResponseDto;
import org.example.searchservice.application.port.out.AuctionSearchRepositoryPort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
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
        List<AuctionSearchDocument> auctionSearchDocuments = auctionSearchElasticRepository.findByAuctionTitle(
                getAuctionSearchRequestDto.getAuctionTitle()
        );
        log.info("Found {} auction search documents for title: {}",
                auctionSearchDocuments.size(),
                getAuctionSearchRequestDto.getAuctionTitle()
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
