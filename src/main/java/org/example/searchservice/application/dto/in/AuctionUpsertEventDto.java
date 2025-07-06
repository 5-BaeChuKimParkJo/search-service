package org.example.searchservice.application.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.searchservice.adapter.in.kafka.event.Image;
import org.springframework.data.annotation.CreatedBy;

import java.time.Instant;
import java.util.List;


@Getter
@NoArgsConstructor
public class AuctionUpsertEventDto {

    private Instant endAt;
    private String title;
    private List<Image> images;
    private List<Long> tagIds;
    private Instant startAt;
    private int version;
    private Instant createdAt;
    private int viewCount;
    private int categoryId;
    private int currentBid;
    private int minimumBid;
    private String sellerUuid;
    private String auctionUuid;
    private String description;
    private boolean isDirectDeal;
    private String thumbnailKey;
    private String thumbnailUrl;
    private String productCondition;
    private String directDealLocation;
    private String categoryName;
    private List<String> tagNames;

    @Builder
    public AuctionUpsertEventDto(Instant endAt, String title, List<Image> images, List<Long> tagIds,
                                 Instant startAt, int version, Instant createdAt, int viewCount,
                                 int categoryId, int currentBid, int minimumBid, String sellerUuid,
                                 String auctionUuid, String description, boolean isDirectDeal,
                                 String thumbnailKey, String thumbnailUrl, String productCondition,
                                 String directDealLocation, String categoryName, List<String> tagNames) {
        this.endAt = endAt;
        this.title = title;
        this.images = images;
        this.tagIds = tagIds;
        this.startAt = startAt;
        this.version = version;
        this.createdAt = createdAt;
        this.viewCount = viewCount;
        this.categoryId = categoryId;
        this.currentBid = currentBid;
        this.minimumBid = minimumBid;
        this.sellerUuid = sellerUuid;
        this.auctionUuid = auctionUuid;
        this.description = description;
        this.isDirectDeal = isDirectDeal;
        this.thumbnailKey = thumbnailKey;
        this.thumbnailUrl = thumbnailUrl;
        this.productCondition = productCondition;
        this.directDealLocation = directDealLocation;
        this.categoryName = categoryName;
        this.tagNames = tagNames;
    }

}
