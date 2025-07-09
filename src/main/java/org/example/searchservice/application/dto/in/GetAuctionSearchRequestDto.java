package org.example.searchservice.application.dto.in;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class GetAuctionSearchRequestDto {

    private String auctionTitle;
    private String categoryName;
    private List<String> tagNames;
    private boolean isDirectDeal;
    private String status;
    private String directDealLocation;
    private String productCondition;
    private String sortBy;
    private List<Object> searchAfter;

    @Builder
    public GetAuctionSearchRequestDto(String auctionTitle, String categoryName, List<String> tagNames,
                                      String status,
                                      boolean isDirectDeal,String directDealLocation, String productCondition, String sortBy, List<Object> searchAfter) {
        this.auctionTitle = auctionTitle;
        this.categoryName = categoryName;
        this.status = status;
        this.tagNames = tagNames;
        this.isDirectDeal = isDirectDeal;
        this.directDealLocation = directDealLocation;
        this.productCondition = productCondition;
        this.sortBy = sortBy;
        this.searchAfter = searchAfter;
    }


}
