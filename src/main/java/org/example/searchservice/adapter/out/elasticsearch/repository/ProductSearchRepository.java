package org.example.searchservice.adapter.out.elasticsearch.repository;

import lombok.RequiredArgsConstructor;
import org.example.searchservice.adapter.out.elasticsearch.mapper.ProductSearchDocumentMapper;
import org.example.searchservice.application.dto.out.ProductUpsertEventDto;
import org.example.searchservice.application.port.out.ProductSearchRepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductSearchRepository implements ProductSearchRepositoryPort {

    private final ProductSearchElasticRepository productSearchElasticRepository;
    private final ProductSearchDocumentMapper productSearchDocumentMapper;
    @Override
    public void save(ProductUpsertEventDto productUpsertEventDto) {

        productSearchElasticRepository.save(productSearchDocumentMapper.toProductSearcDocument(productUpsertEventDto));

    }
}
