package org.example.searchservice.adapter.in.kafka.event.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductBatchEvent {
    private Long id;
    private String productUuid;
    private String saleMemberUuid;
    private String title;
    private String categoryId;
    private String description;
    private String productCondition;
    private Boolean isDirectDeal;
    private String directDealLocation;
    private Boolean isHide;
    private String status;
    private String thumbnailKey;
    private Integer viewCount;
    private Integer price;
    private String ticketUuid;
    private List<ImageUrl> imageUrlList;
    private List<Integer> tagIdList;
    private Boolean isDeleted;
    private String createdAt;

    @Builder
    public ProductBatchEvent(Long id, String productUuid, String saleMemberUuid, String title, String categoryId,
                             String description, String productCondition, Boolean isDirectDeal,
                             String directDealLocation, Boolean isHide, String status, String thumbnailKey,
                             Integer viewCount, Integer price, String ticketUuid, List<ImageUrl> imageUrlList,
                             List<Integer> tagIdList, Boolean isDeleted, String createdAt) {
        this.id = id;
        this.productUuid = productUuid;
        this.saleMemberUuid = saleMemberUuid;
        this.title = title;
        this.categoryId = categoryId;
        this.description = description;
        this.productCondition = productCondition;
        this.isDirectDeal = isDirectDeal;
        this.directDealLocation = directDealLocation;
        this.isHide = isHide;
        this.status = status;
        this.thumbnailKey = thumbnailKey;
        this.viewCount = viewCount;
        this.price = price;
        this.ticketUuid = ticketUuid;
        this.imageUrlList = imageUrlList;
        this.tagIdList = tagIdList;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }


}
