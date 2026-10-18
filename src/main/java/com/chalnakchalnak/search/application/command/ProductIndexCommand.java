package com.chalnakchalnak.search.application.command;

import com.chalnakchalnak.search.domain.ProductImage;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductIndexCommand {

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
  private String createdAt;
  private String op;

  public Instant getCreatedAtAsInstant() {
    return LocalDateTime.parse(createdAt).toInstant(java.time.ZoneOffset.UTC);
  }
}
