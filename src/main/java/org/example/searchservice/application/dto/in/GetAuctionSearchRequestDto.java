package org.example.searchservice.application.dto.in;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetAuctionSearchRequestDto {

    private String auctionUuid;
    private String auctionTitle;
    private String description;
    private String status;
    private String directDealLocation;

    @Builder
    public GetAuctionSearchRequestDto(String auctionUuid, String auctionTitle, String description, String status, String directDealLocation) {
        this.auctionUuid = auctionUuid;
        this.auctionTitle = auctionTitle;
        this.description = description;
        this.status = status;
        this.directDealLocation = directDealLocation;
    }
}
