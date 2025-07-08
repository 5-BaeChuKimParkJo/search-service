package org.example.searchservice.application.dto.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.searchservice.adapter.in.kafka.event.product.ImageUrl;

import java.time.Instant;
import java.util.List;


@Getter
@NoArgsConstructor
public class GetProductSearchResponseDto {

    private Long id;
    private String productUuid;
    private String saleMemberUuid;
    private String title;
    private int categoryId;
    private String description;
    private String productCondition;
    private boolean isDirectDeal;
    private String directDealLocation;
    private Boolean isHide;
    private String status;
    private String thumbnailKey;
    private int viewCount;
    private int price;
    private String ticketUuid;
    private List<ImageUrl> imageUrlList;
    private List<Long> tagIdList;
    private Boolean isDeleted;
    private Instant createdAt;
    private List<String> tagNames;
    private String categoryName;

    @Builder
    public GetProductSearchResponseDto(Long id, String productUuid, String saleMemberUuid, String title, int categoryId,
                                       String description, String productCondition, boolean isDirectDeal,
                                       String directDealLocation, Boolean isHide, String status, String thumbnailKey,
                                       int viewCount, int price, String ticketUuid, List<ImageUrl> imageUrlList,
                                       List<Long> tagIdList, Boolean isDeleted, Instant createdAt,
                                       List<String> tagNames, String categoryName) {
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
        this.tagNames = tagNames;
        this.categoryName = categoryName;
    }

}
