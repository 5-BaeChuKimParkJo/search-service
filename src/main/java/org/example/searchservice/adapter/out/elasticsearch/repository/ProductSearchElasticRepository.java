package org.example.searchservice.adapter.out.elasticsearch.repository;

import org.example.searchservice.adapter.out.elasticsearch.entity.ProductSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductSearchElasticRepository extends ElasticsearchRepository<ProductSearchDocument, String> {

}
