package com.chalnakchalnak.search.application.converter;

import com.chalnakchalnak.search.application.command.ProductIndexCommand;
import com.chalnakchalnak.search.application.command.UpsertProductCommand;
import com.chalnakchalnak.search.application.result.CategoryResult;
import com.chalnakchalnak.search.application.result.TagResult;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ProductBatchEventConverter {

  public UpsertProductCommand toProductUpsertDto(
      ProductIndexCommand productDto,
      CategoryResult categoryResponseDto,
      List<TagResult> tagResponseDtos) {

    return UpsertProductCommand.builder()
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
        .tagNames(tagResponseDtos.stream().map(TagResult::getTagName).toList())
        .createdAt(productDto.getCreatedAtAsInstant())
        .build();
  }
}
