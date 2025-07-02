package org.example.searchservice.adapter.in.vo.out;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public class NextCursorVo {

    private String lastAuctionUuid;
    private Instant lastAuctionCreatedAt;
    private int lastAuctionCurrentBid;
    private int lastAuctionViewCount;

    @Builder
    public NextCursorVo(String lastAuctionUuid, Instant lastAuctionCreatedAt,
                        int lastAuctionCurrentBid, int lastAuctionViewCount) {
        this.lastAuctionUuid = lastAuctionUuid;
        this.lastAuctionCreatedAt = lastAuctionCreatedAt;
        this.lastAuctionCurrentBid = lastAuctionCurrentBid;
        this.lastAuctionViewCount = lastAuctionViewCount;
    }

}
