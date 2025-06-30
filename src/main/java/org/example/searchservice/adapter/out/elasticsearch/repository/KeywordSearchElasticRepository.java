package org.example.searchservice.adapter.out.elasticsearch.repository;

import org.example.searchservice.adapter.out.elasticsearch.entity.KeywordSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface KeywordSearchElasticRepository extends ElasticsearchRepository<KeywordSearchDocument, String> {

    Optional<KeywordSearchDocument> findByKeyword(String keyword);

}
