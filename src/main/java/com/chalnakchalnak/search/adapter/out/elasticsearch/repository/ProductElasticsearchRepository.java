package com.chalnakchalnak.search.adapter.out.elasticsearch.repository;

import com.chalnakchalnak.search.adapter.out.elasticsearch.entity.ProductSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductElasticsearchRepository
    extends ElasticsearchRepository<ProductSearchDocument, String> {}
