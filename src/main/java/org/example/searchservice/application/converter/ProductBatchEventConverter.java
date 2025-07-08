package org.example.searchservice.application.converter;

import org.example.searchservice.application.dto.in.CategoryResponseDto;
import org.example.searchservice.application.dto.in.TagResponseDto;
import org.example.searchservice.application.dto.out.ProductBatchEventDto;
import org.example.searchservice.application.dto.out.ProductUpsertEventDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductBatchEventConverter {

    public ProductUpsertEventDto toProductUpsertDto(ProductBatchEventDto productDto,
                                                    CategoryResponseDto categoryResponseDto,
                                                    List<TagResponseDto> tagResponseDtos) {


        return ProductUpsertEventDto.builder()
                .productUuid(productDto.getProductUuid())
                .title(productDto.getTitle())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .isHide(productDto.getIsHide())
                .directDealLocation(productDto.getDirectDealLocation())
                .productCondition(productDto.getProductCondition())
                .isDirectDeal(productDto.isDirectDeal())
                .thumbnailKey(productDto.getThumbnailKey())
                .saleMemberUuid(productDto.getSaleMemberUuid())
                .status(productDto.getStatus())
                .ticketUuid(productDto.getTicketUuid())
                .id(productDto.getId())
                .viewCount(productDto.getViewCount())
                .isDeleted(productDto.getIsDeleted())
                .productUuid(productDto.getProductUuid())
                .imageUrlList(productDto.getImageUrlList())
                .categoryId(categoryResponseDto.getCategoryId())
                .categoryName(categoryResponseDto.getCategoryName())
                .tagIdList(productDto.getTagIdList())
                .tagNames(tagResponseDtos.stream().map(TagResponseDto::getTagName).toList())
                .createdAt(productDto.getCreatedAtAsInstant())
                .build();
    }
}
