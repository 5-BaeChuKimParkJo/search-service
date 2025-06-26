package org.example.searchservice.adapter.in.vo.in;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateAuctionSearchRequestVo {

    private String auctionUuid;
    private String auctionTitle;
    private String description;
    private String status;
    private String thumbnailKey;
    private String directDealLocation;

    @Builder
    public CreateAuctionSearchRequestVo(String auctionUuid, String auctionTitle, String description,
                                        String status, String thumbnailKey, String directDealLocation) {
        this.auctionUuid = auctionUuid;
        this.auctionTitle = auctionTitle;
        this.description = description;
        this.status = status;
        this.thumbnailKey = thumbnailKey;
        this.directDealLocation = directDealLocation;
    }

}
