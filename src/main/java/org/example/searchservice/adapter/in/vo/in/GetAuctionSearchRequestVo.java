package org.example.searchservice.adapter.in.vo.in;


import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class GetAuctionSearchRequestVo {

    private String auctionTitle;
    private String categoryName;
    private List<String> tagNames;
    private String status;
    private boolean isDirectDeal;
    private String directDealLocation;
    private String productCondition;
    private String sortBy;
    private List<Object> searchAfter;

    @Builder
    public GetAuctionSearchRequestVo(String auctionTitle, String categoryName, List<String> tagNames,
                                      String status,
                                      boolean isDirectDeal,String directDealLocation, String productCondition, String sortBy, List<Object> searchAfter) {
        this.auctionTitle = auctionTitle;
        this.categoryName = categoryName;
        this.tagNames = tagNames;
        this.status = status;
        this.isDirectDeal = isDirectDeal;
        this.directDealLocation = directDealLocation;
        this.productCondition = productCondition;
        this.sortBy = sortBy;
        this.searchAfter = searchAfter;
    }

}
