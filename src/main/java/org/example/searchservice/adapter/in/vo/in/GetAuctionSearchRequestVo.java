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
    private boolean isDirectDeal;
    private String productCondition;
    private String sortBy;
    private List<Object> searchAfter;

    @Builder
    public GetAuctionSearchRequestVo(String auctionTitle, String categoryName, List<String> tagNames,
                                      boolean isDirectDeal, String productCondition, String sortBy, List<Object> searchAfter) {
        this.auctionTitle = auctionTitle;
        this.categoryName = categoryName;
        this.tagNames = tagNames;
        this.isDirectDeal = isDirectDeal;
        this.productCondition = productCondition;
        this.sortBy = sortBy;
        this.searchAfter = searchAfter;
    }

}
