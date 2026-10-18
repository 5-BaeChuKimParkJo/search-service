package com.chalnakchalnak.search.adapter.out.elasticsearch.repository;

import com.chalnakchalnak.search.adapter.out.elasticsearch.entity.KeywordSearchDocument;
import java.util.Optional;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface KeywordElasticsearchRepository
    extends ElasticsearchRepository<KeywordSearchDocument, String> {

  Optional<KeywordSearchDocument> findByKeyword(String keyword);
}
