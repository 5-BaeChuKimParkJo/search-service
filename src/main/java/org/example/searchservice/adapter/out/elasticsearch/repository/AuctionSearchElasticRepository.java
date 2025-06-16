package org.example.searchservice.adapter.out.elasticsearch.repository;

import org.example.searchservice.adapter.out.elasticsearch.entity.AuctionSearchDocument;
import org.example.searchservice.application.dto.out.GetAuctionSearchResponseDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface AuctionSearchElasticRepository extends ElasticsearchRepository<AuctionSearchDocument, String> {

    List<AuctionSearchDocument> findByTitle(String title);


}
