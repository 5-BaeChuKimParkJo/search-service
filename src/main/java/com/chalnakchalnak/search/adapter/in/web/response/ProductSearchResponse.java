package com.chalnakchalnak.search.adapter.in.web.response;

import com.chalnakchalnak.search.domain.ProductImage;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductSearchResponse {

  private Long id;
  private String productUuid;
  private String saleMemberUuid;
  private String title;
  private int categoryId;
  private String description;
  private String productCondition;
  private boolean isDirectDeal;
  private String directDealLocation;
  private Boolean isHide;
  private String status;
  private String thumbnailKey;
  private int viewCount;
  private int price;
  private String ticketUuid;
  private List<ProductImage> imageUrlList;
  private List<Long> tagIdList;
  private Boolean isDeleted;
  private Instant createdAt;
  private List<String> tagNames;
  private String categoryName;

  @Builder
  public ProductSearchResponse(
      Long id,
      String productUuid,
      String saleMemberUuid,
      String title,
      int categoryId,
      String description,
      String productCondition,
      boolean isDirectDeal,
      String directDealLocation,
      Boolean isHide,
      String status,
      String thumbnailKey,
      int viewCount,
      int price,
      String ticketUuid,
      List<ProductImage> imageUrlList,
      List<Long> tagIdList,
      Boolean isDeleted,
      Instant createdAt,
      List<String> tagNames,
      String categoryName) {
    this.id = id;
    this.productUuid = productUuid;
    this.saleMemberUuid = saleMemberUuid;
    this.title = title;
    this.categoryId = categoryId;
    this.description = description;
    this.productCondition = productCondition;
    this.isDirectDeal = isDirectDeal;
    this.directDealLocation = directDealLocation;
    this.isHide = isHide;
    this.status = status;
    this.thumbnailKey = thumbnailKey;
    this.viewCount = viewCount;
    this.price = price;
    this.ticketUuid = ticketUuid;
    this.imageUrlList = imageUrlList;
    this.tagIdList = tagIdList;
    this.isDeleted = isDeleted;
    this.createdAt = createdAt;
    this.tagNames = tagNames;
    this.categoryName = categoryName;
  }
}
