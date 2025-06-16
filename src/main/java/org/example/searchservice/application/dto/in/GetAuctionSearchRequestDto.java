package org.example.searchservice.application.dto.in;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetAuctionSearchRequestDto {

    private String auctionUuid;
    private String title;
    private String description;
    private String status;
    private String directDealLocation;

    @Builder
    public GetAuctionSearchRequestDto(String auctionUuid, String title, String description, String status, String directDealLocation) {
        this.auctionUuid = auctionUuid;
        this.title = title;
        this.description = description;
        this.status = status;
        this.directDealLocation = directDealLocation;
    }
}
