package com.chalnakchalnak.search.adapter.out.elasticsearch.repository;

import com.chalnakchalnak.search.adapter.out.elasticsearch.entity.AuctionSearchDocument;
import java.util.List;
import java.util.Optional;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AuctionElasticsearchRepository
    extends ElasticsearchRepository<AuctionSearchDocument, String> {

  List<AuctionSearchDocument> findByAuctionTitle(String title);

  Optional<AuctionSearchDocument> findByAuctionUuid(String auctionUuid);
}
