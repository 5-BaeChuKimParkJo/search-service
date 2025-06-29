package org.example.searchservice.adapter.out.elasticsearch.entity;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.searchservice.adapter.in.kafka.event.Image;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "auction_search")
public class AuctionSearchDocument {

    @Id
    private String id;
    @Field(type = FieldType.Keyword)
    private String auctionUuid;

    @Field(type = FieldType.Text)
    private String auctionTitle;

    @Field(type = FieldType.Text)
    private String auctionDescription;

    @Field(type = FieldType.Keyword)
    private String status;

    @Field(type = FieldType.Keyword)
    private String thumbnailKey;

    @Field(type = FieldType.Text)
    private String directDealLocation;

    @Field(type = FieldType.Keyword)
    private String thumbnailUrl;

    @Field(type = FieldType.Nested)
    private List<Image> images;

    @Field(type = FieldType.Keyword)
    private String sellerUuid;

    @Field(type = FieldType.Date)
    private Instant startAt;

    @Field(type = FieldType.Date)
    private Instant endAt;

    @Field(type = FieldType.Integer)
    private int version;

    @Field(type = FieldType.Date)
    private Instant createdAt;

    @Field(type = FieldType.Integer)
    private int viewCount;

    @Field(type = FieldType.Integer)
    private int currentBid;

    @Field(type = FieldType.Integer)
    private int minimumBid;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Boolean)
    private boolean isDirectDeal;

    @Field(type = FieldType.Keyword)
    private String productCondition;

    // category
    @Field(type = FieldType.Integer)
    private int categoryId;

    @Field(type = FieldType.Text)
    private String categoryName;

    @Field(type = FieldType.Text)
    private String categoryDescription;

    @Field(type = FieldType.Keyword)
    private String categoryThumbnailKey;

    // tag
    @Field(type = FieldType.Long)
    private List<Long> tagId;

    @Field(type = FieldType.Text)
    private List<String> tagNames;

    @Builder
    public AuctionSearchDocument(String auctionUuid, String auctionTitle, String auctionDescription, String status,
                                 String thumbnailKey, String directDealLocation, String thumbnailUrl, List<Image> images,
                                 String sellerUuid, Instant startAt, Instant endAt, int version, Instant createdAt,
                                 int viewCount, int currentBid, int minimumBid, String description, boolean isDirectDeal,
                                 String productCondition, int categoryId, String categoryName, String categoryDescription,
                                 String categoryThumbnailKey, List<Long> tagId, List<String> tagNames) {
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
