package org.example.searchservice.adapter.in.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public class NextCursorProductVo {

    private String lastProductUuid;
    private Instant lastProductCreatedAt;
    private int lastProductViewCount;
    private int lastProductPrice;

    @Builder
    public NextCursorProductVo(String lastProductUuid, Instant lastProductCreatedAt,
                               int lastProductViewCount, int lastProductPrice) {
        this.lastProductUuid = lastProductUuid;
        this.lastProductCreatedAt = lastProductCreatedAt;
        this.lastProductViewCount = lastProductViewCount;
        this.lastProductPrice = lastProductPrice;
    }
}
