package org.example.searchservice.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.adapter.in.kafka.event.product.ProductEvent;
import org.example.searchservice.adapter.in.kafka.mapper.ProductMessageMapper;
import org.example.searchservice.adapter.out.elasticsearch.querybuilder.ProductSearchQueryBuilder;
import org.example.searchservice.adapter.out.feign.CategoryClient;
import org.example.searchservice.adapter.out.feign.TagClient;
import org.example.searchservice.application.converter.ProductBatchEventConverter;
import org.example.searchservice.application.dto.in.CategoryResponseDto;
import org.example.searchservice.application.dto.in.GetProductSearchRequestDto;
import org.example.searchservice.application.dto.in.TagResponseDto;
import org.example.searchservice.application.dto.out.GetProductSearchResponseDto;
import org.example.searchservice.application.dto.out.ProductBatchEventDto;
import org.example.searchservice.application.port.in.ProductSearchUseCase;
import org.example.searchservice.application.port.out.ProductSearchRepositoryPort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSearchService implements ProductSearchUseCase {

    private final ProductSearchRepositoryPort productSearchRepositoryPort;
    private final ProductMessageMapper productMessageMapper;
    private final ProductBatchEventConverter productBatchEventConverter;
    private final ProductSearchQueryBuilder productSearchQueryBuilder;
    private final CategoryClient categoryClient;
    private final TagClient tagClient;

    @Override
    public List<GetProductSearchResponseDto> searchProduct(GetProductSearchRequestDto getProductSearchRequestDto) {

        NativeQuery nativeQuery = productSearchQueryBuilder.buildProductSearchQuery(getProductSearchRequestDto);
        log.info("Executing search with query: {}", nativeQuery.getQuery());
        return productSearchRepositoryPort.search(nativeQuery);
    }

    @Override
    public void saveProductBulk(List<ProductEvent> productEvents) {

        productEvents.stream()
                     .forEach(productEvent -> {

                         try {
                             ProductBatchEventDto productBatchEventDto = productMessageMapper.toProductBatchEventDto(
                                     productEvent.getPayload(),
                                     productEvent.getOp()
                             );

                             CategoryResponseDto categoryResponseDto = categoryClient.getCategory(productBatchEventDto.getCategoryId());
                             List<TagResponseDto> tagResponseDtoList = productBatchEventDto.getTagIdList().stream()
                                     .map(tagId -> {
                                         try {
                                             return tagClient.getTagById(tagId);
                                         } catch (Exception e) {
                                             throw new RuntimeException("Failed to fetch tag with ID: " + tagId, e);
                                         }
                                     })
                                     .toList();

                             productSearchRepositoryPort.save(productBatchEventConverter.toProductUpsertDto(
                                     productBatchEventDto,
                                     categoryResponseDto,
                                     tagResponseDtoList
                             ));


                         } catch (JsonProcessingException e) {
                             throw new RuntimeException(e);
                         }
                     });

    }
}
