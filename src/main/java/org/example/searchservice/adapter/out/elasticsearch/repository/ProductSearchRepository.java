package org.example.searchservice.adapter.out.elasticsearch.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.adapter.out.elasticsearch.entity.ProductSearchDocument;
import org.example.searchservice.adapter.out.elasticsearch.mapper.ProductSearchDocumentMapper;
import org.example.searchservice.application.dto.out.GetProductSearchResponseDto;
import org.example.searchservice.application.dto.out.ProductUpsertEventDto;
import org.example.searchservice.application.port.out.ProductSearchRepositoryPort;
import org.example.searchservice.common.exception.BaseException;
import org.example.searchservice.common.response.BaseResponseStatus;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductSearchRepository implements ProductSearchRepositoryPort {

    private final ProductSearchElasticRepository productSearchElasticRepository;
    private final ProductSearchDocumentMapper productSearchDocumentMapper;
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<GetProductSearchResponseDto> search(NativeQuery nativeQuery) {

        try {

            log.info("쿼리: {}", nativeQuery.getQuery());
            log.info("정렬: {}", nativeQuery.getSort());
            log.info("searchAfter: {}", nativeQuery.getSearchAfter());

            SearchHits<ProductSearchDocument> hits = elasticsearchOperations
                    .search(nativeQuery, ProductSearchDocument.class);

            if (hits.isEmpty()) {
                log.warn("검색 결과가 없습니다.");
                return List.of(); // 또는 null, 또는 BaseException 등 상황에 맞게
            }

            return hits.stream()
                    .map(hit -> productSearchDocumentMapper.toGetProductSearchResponseDto(hit.getContent()))
                    .toList();

        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_PRODUCT_SEARCH);
        }

    }

    @Override
    public void save(ProductUpsertEventDto productUpsertEventDto) {

        productSearchElasticRepository.save(productSearchDocumentMapper.toProductSearcDocument(productUpsertEventDto));
        elasticsearchOperations.indexOps(ProductSearchDocument.class).refresh();
    }
}
