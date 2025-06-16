package org.example.searchservice.adapter.in.vo.out;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetAuctionSearchResponseVo {
    private String auctionUuid;
    private String title;
    private String description;
    private String status;
    private String thumbnailKey;
    private String directDealLocation;


    @Builder
    public GetAuctionSearchResponseVo(String auctionUuid, String title, String description,
                                      String status, String thumbnailKey, String directDealLocation) {
        this.auctionUuid = auctionUuid;
        this.title = title;
        this.description = description;
        this.status = status;
        this.thumbnailKey = thumbnailKey;
        this.directDealLocation = directDealLocation;
    }
}
