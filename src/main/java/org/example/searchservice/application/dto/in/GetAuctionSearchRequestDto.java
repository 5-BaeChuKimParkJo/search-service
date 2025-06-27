package org.example.searchservice.application.dto.in;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetAuctionSearchRequestDto {

    private String auctionTitle;
    private String categoryName;
    private List<String> tagNames;
    private boolean isDirectDeal;
    private String productCondition;

    @Builder
    public GetAuctionSearchRequestDto(String auctionTitle, String categoryName, List<String> tagNames,
                                      boolean isDirectDeal, String productCondition) {
        this.auctionTitle = auctionTitle;
        this.categoryName = categoryName;
        this.tagNames = tagNames;
        this.isDirectDeal = isDirectDeal;
        this.productCondition = productCondition;
    }


}
