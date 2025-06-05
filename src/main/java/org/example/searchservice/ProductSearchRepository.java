//package org.example.searchservice;
//
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//
//import java.util.List;
//
//public interface ProductSearchRepository extends ElasticsearchRepository<Product, String> {
//    List<Product> findByNameContaining(String keyword);
//}