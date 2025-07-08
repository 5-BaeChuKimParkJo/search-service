package org.example.searchservice.application.dto.out;


import lombok.Getter;
import lombok.Setter;
import org.example.searchservice.adapter.in.kafka.event.product.ImageUrl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProductBatchEventDto {

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
    private String createdAt;
    private String op;

    public Instant getCreatedAtAsInstant() {
        return LocalDateTime.parse(createdAt).toInstant(java.time.ZoneOffset.UTC);
    }
}
