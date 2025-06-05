package org.example.searchservice.domain.model;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductSearchDomain {

    private String productUuid;
    private String productThumbnailUrl;
    private String title;
    private Integer price;
    private String productCondition;
    private Integer likeCount;
    private Integer chattingMemberCount;
    private Boolean isDirectDeal;
    private String DirectDealLocation;
    private String postCreatedTime;

    @Builder
    public ProductSearchDomain(String productUuid, String productThumbnailUrl, String title,
                               Integer price, String productCondition, Integer likeCount,
                               Integer chattingMemberCount, Boolean isDirectDeal,
                               String directDealLocation, String postCreatedTime) {
        this.productUuid = productUuid;
        this.productThumbnailUrl = productThumbnailUrl;
        this.title = title;
        this.price = price;
        this.productCondition = productCondition;
        this.likeCount = likeCount;
        this.chattingMemberCount = chattingMemberCount;
        this.isDirectDeal = isDirectDeal;
        this.DirectDealLocation = directDealLocation;
        this.postCreatedTime = postCreatedTime;
    }


}
