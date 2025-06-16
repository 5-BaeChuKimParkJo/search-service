package org.example.searchservice.adapter.in.vo.in;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class GetAuctionSearchRequestVo {

    private String auctionUuid;
    private String title;
    private String description;
    private String status;
    private String directDealLocation;

}
