package org.example.searchservice.application.dto.in;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetProductSearchRequestDto {

    private String productTitle;
    private String categoryName;
    private List<String> tagNames;
    private boolean isDirectDeal;
    private String directDealLocation;
    private String productCondition;
    private String sortBy;
    private String searchAfter1;
    private String searchAfter2;

    @Builder
    public GetProductSearchRequestDto(String productTitle, String categoryName, List<String> tagNames,
                                       boolean isDirectDeal, String directDealLocation, String productCondition,
                                       String sortBy, String searchAfter1, String searchAfter2) {
        this.productTitle = productTitle;
        this.categoryName = categoryName;
        this.tagNames = tagNames;
        this.isDirectDeal = isDirectDeal;
        this.directDealLocation = directDealLocation;
        this.productCondition = productCondition;
        this.sortBy = sortBy;
        this.searchAfter1 = searchAfter1;
        this.searchAfter2 = searchAfter2;
    }
}
