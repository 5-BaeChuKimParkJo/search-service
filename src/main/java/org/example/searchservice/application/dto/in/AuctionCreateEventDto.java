package org.example.searchservice.application.dto.in;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.searchservice.adapter.in.kafka.event.Image;

import java.util.List;

@Getter
@NoArgsConstructor
public class AuctionCreateEventDto {

    private String endAt;
    private String title;
    private List<Image> images;
    private List<Long> tagIds;
    private String startAt;
    private int version;
    private String createdAt;
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
}
