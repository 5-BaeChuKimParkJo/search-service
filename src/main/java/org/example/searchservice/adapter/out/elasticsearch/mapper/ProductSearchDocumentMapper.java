package org.example.searchservice.adapter.out.elasticsearch.mapper;


import org.example.searchservice.adapter.out.elasticsearch.entity.ProductSearchDocument;
import org.example.searchservice.application.dto.out.GetProductSearchResponseDto;
import org.example.searchservice.application.dto.out.ProductUpsertEventDto;
import org.springframework.stereotype.Component;

@Component
public class ProductSearchDocumentMapper {

    public ProductSearchDocument toProductSearcDocument(ProductUpsertEventDto productUpsertEventDto) {
        return ProductSearchDocument.builder()
                .productUuid(productUpsertEventDto.getProductUuid())
                .saleMemberUuid(productUpsertEventDto.getSaleMemberUuid())
                .title(productUpsertEventDto.getTitle())
                .categoryId(productUpsertEventDto.getCategoryId())
                .description(productUpsertEventDto.getDescription())
                .productCondition(productUpsertEventDto.getProductCondition())
                .isDirectDeal(productUpsertEventDto.isDirectDeal())
                .directDealLocation(productUpsertEventDto.getDirectDealLocation())
                .isHide(productUpsertEventDto.getIsHide())
                .status(productUpsertEventDto.getStatus())
                .thumbnailKey(productUpsertEventDto.getThumbnailKey())
                .viewCount(productUpsertEventDto.getViewCount())
                .price(productUpsertEventDto.getPrice())
                .ticketUuid(productUpsertEventDto.getTicketUuid())
                .imageUrlList(productUpsertEventDto.getImageUrlList())
                .tagIdList(productUpsertEventDto.getTagIdList())
                .isDeleted(productUpsertEventDto.getIsDeleted())
                .createdAt(productUpsertEventDto.getCreatedAt())
                .tagNames(productUpsertEventDto.getTagNames())
                .categoryName(productUpsertEventDto.getCategoryName())
                .build();
    }

    public GetProductSearchResponseDto toGetProductSearchResponseDto(ProductSearchDocument productSearchDocument) {
        return GetProductSearchResponseDto.builder()
                .productUuid(productSearchDocument.getProductUuid())
                .saleMemberUuid(productSearchDocument.getSaleMemberUuid())
                .title(productSearchDocument.getTitle())
                .categoryId(productSearchDocument.getCategoryId())
                .description(productSearchDocument.getDescription())
                .productCondition(productSearchDocument.getProductCondition())
                .isDirectDeal(productSearchDocument.isDirectDeal())
                .directDealLocation(productSearchDocument.getDirectDealLocation())
                .isHide(productSearchDocument.isHide())
                .status(productSearchDocument.getStatus())
                .thumbnailKey(productSearchDocument.getThumbnailKey())
                .viewCount(productSearchDocument.getViewCount())
                .price(productSearchDocument.getPrice())
                .ticketUuid(productSearchDocument.getTicketUuid())
                .imageUrlList(productSearchDocument.getImageUrlList())
                .tagIdList(productSearchDocument.getTagIdList())
                .isDeleted(productSearchDocument.isDeleted())
                .createdAt(productSearchDocument.getCreatedAt())
                .tagNames(productSearchDocument.getTagNames())
                .categoryName(productSearchDocument.getCategoryName())
                .build();
    }
}
