package org.example.searchservice.application.dto.out;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.searchservice.adapter.in.kafka.event.Image;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetAuctionSearchResponseDto {

    private String id;
    private String auctionUuid;
    private String auctionTitle;
    private String auctionDescription;
    private String status;
    private String thumbnailKey;
    private String directDealLocation;
    private String thumbnailUrl;
    private List<Image> images;
    private String sellerUuid;
    private String startAt;
    private String endAt;
    private int version;
    private String createdAt;
    private int viewCount;
    private int currentBid;
    private int minimumBid;
    private String description;
    private boolean isDirectDeal;
    private String productCondition;

    //category
    private int categoryId;
    private String categoryName;
    private String categoryDescription;
    private String categoryThumbnailKey;
    //tag
    private List<Long> tagId;
    private List<String> tagNames;

    @Builder
    public GetAuctionSearchResponseDto(String id, String auctionUuid, String auctionTitle, String auctionDescription,
                                       String status, String thumbnailKey, String directDealLocation, String thumbnailUrl,
                                       List<Image> images, String sellerUuid, String startAt, String endAt, int version,
                                       String createdAt, int viewCount, int currentBid, int minimumBid, String description,
                                       boolean isDirectDeal, String productCondition, int categoryId, String categoryName,
                                       String categoryDescription, String categoryThumbnailKey, List<Long> tagId,
                                       List<String> tagNames) {
        this.id = id;
        this.auctionUuid = auctionUuid;
        this.auctionTitle = auctionTitle;
        this.auctionDescription = auctionDescription;
        this.status = status;
        this.thumbnailKey = thumbnailKey;
        this.directDealLocation = directDealLocation;
        this.thumbnailUrl = thumbnailUrl;
        this.images = images;
        this.sellerUuid = sellerUuid;
        this.startAt = startAt;
        this.endAt = endAt;
        this.version = version;
        this.createdAt = createdAt;
        this.viewCount = viewCount;
        this.currentBid = currentBid;
        this.minimumBid = minimumBid;
        this.description = description;
        this.isDirectDeal = isDirectDeal;
        this.productCondition = productCondition;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryThumbnailKey = categoryThumbnailKey;
        this.tagId = tagId;
        this.tagNames = tagNames;
    }


}
