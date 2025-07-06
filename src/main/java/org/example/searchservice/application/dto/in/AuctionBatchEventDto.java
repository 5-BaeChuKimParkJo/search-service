package org.example.searchservice.application.dto.in;

import lombok.Getter;
import lombok.Setter;
import org.example.searchservice.adapter.in.kafka.event.Image;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class AuctionBatchEventDto {

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
    private String op;
    private String categoryName;
    private List<String> tagNames;
}
