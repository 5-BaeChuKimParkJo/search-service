package org.example.searchservice.adapter.in.vo.in;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class GetAuctionSearchRequestVo {

    private String auctionUuid;
    private String auctionTitle;
    private String description;
    private String status;
    private String directDealLocation;

    @Builder
    public GetAuctionSearchRequestVo(String auctionUuid, String auctionTitle, String description,
                                     String status, String directDealLocation) {
        this.auctionUuid = auctionUuid;
        this.auctionTitle = auctionTitle;
        this.description = description;
        this.status = status;
        this.directDealLocation = directDealLocation;
    }

}
