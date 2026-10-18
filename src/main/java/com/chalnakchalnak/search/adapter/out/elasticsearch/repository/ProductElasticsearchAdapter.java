package com.chalnakchalnak.search.adapter.out.elasticsearch.repository;

import com.chalnakchalnak.search.adapter.out.elasticsearch.entity.ProductSearchDocument;
import com.chalnakchalnak.search.adapter.out.elasticsearch.mapper.ProductSearchDocumentMapper;
import com.chalnakchalnak.search.adapter.out.elasticsearch.querybuilder.ProductSearchQueryBuilder;
import com.chalnakchalnak.search.application.command.UpsertProductCommand;
import com.chalnakchalnak.search.application.port.out.ProductSearchRepositoryPort;
import com.chalnakchalnak.search.application.query.ProductSearchQuery;
import com.chalnakchalnak.search.application.result.ProductSearchResult;
import com.chalnakchalnak.search.common.exception.BaseException;
import com.chalnakchalnak.search.common.response.BaseResponseStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductElasticsearchAdapter implements ProductSearchRepositoryPort {

  private final ProductElasticsearchRepository productSearchElasticRepository;
  private final ProductSearchDocumentMapper productSearchDocumentMapper;
  private final ElasticsearchOperations elasticsearchOperations;
  private final ProductSearchQueryBuilder queryBuilder;

  @Override
  public List<ProductSearchResult> search(ProductSearchQuery query) {
    NativeQuery nativeQuery = queryBuilder.buildProductSearchQuery(query);

    try {

      log.info("쿼리: {}", nativeQuery.getQuery());
      log.info("정렬: {}", nativeQuery.getSort());
      log.info("searchAfter: {}", nativeQuery.getSearchAfter());

      SearchHits<ProductSearchDocument> hits =
          elasticsearchOperations.search(nativeQuery, ProductSearchDocument.class);

      if (hits.isEmpty()) {
        log.warn("검색 결과가 없습니다.");
      }

      return hits.stream()
          .map(hit -> productSearchDocumentMapper.toGetProductSearchResponseDto(hit.getContent()))
          .toList();

    } catch (Exception e) {
      throw new BaseException(BaseResponseStatus.FAILED_PRODUCT_SEARCH);
    }
  }

  @Override
  public void save(UpsertProductCommand productUpsertEventDto) {

    productSearchElasticRepository.save(
        productSearchDocumentMapper.toProductSearcDocument(productUpsertEventDto));
    elasticsearchOperations.indexOps(ProductSearchDocument.class).refresh();
  }
}
