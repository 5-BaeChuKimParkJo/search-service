package org.example.searchservice.adapter.in.vo.out;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NextCursor {

    private String lastAuctionUuid;
    private String lastAuctionCreatedAt;
    private String lastAuctionCurrentBid;
    private String lastAuctionViewCount;

    @Builder
    public NextCursor(String lastAuctionUuid, String lastAuctionCreatedAt,
                      String lastAuctionCurrentBid, String lastAuctionViewCount) {
        this.lastAuctionUuid = lastAuctionUuid;
        this.lastAuctionCreatedAt = lastAuctionCreatedAt;
        this.lastAuctionCurrentBid = lastAuctionCurrentBid;
        this.lastAuctionViewCount = lastAuctionViewCount;
    }

}
