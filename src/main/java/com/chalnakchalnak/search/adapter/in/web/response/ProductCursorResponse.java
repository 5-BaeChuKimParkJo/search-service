package com.chalnakchalnak.search.adapter.in.web.response;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCursorResponse {

  private String lastProductUuid;
  private Instant lastProductCreatedAt;
  private int lastProductViewCount;
  private int lastProductPrice;

  @Builder
  public ProductCursorResponse(
      String lastProductUuid,
      Instant lastProductCreatedAt,
      int lastProductViewCount,
      int lastProductPrice) {
    this.lastProductUuid = lastProductUuid;
    this.lastProductCreatedAt = lastProductCreatedAt;
    this.lastProductViewCount = lastProductViewCount;
    this.lastProductPrice = lastProductPrice;
  }
}
