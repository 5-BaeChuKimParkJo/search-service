package org.example.searchservice.application.dto.in;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuctionDeleteEventDto {

    private String auctionUuid;

    @Builder
    public AuctionDeleteEventDto(String auctionUuid) {
        this.auctionUuid = auctionUuid;
    }
}
