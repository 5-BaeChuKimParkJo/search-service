package org.example.searchservice.adapter.out.elasticsearch.entity;


import lombok.*;
import org.example.searchservice.adapter.in.kafka.event.product.ImageUrl;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Document(indexName = "product_search")
@Getter
@Setter
@NoArgsConstructor
public class ProductSearchDocument {


    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String productUuid;

    @Field(type = FieldType.Keyword)
    private String saleMemberUuid;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Keyword)
    private int categoryId;

    @Field(type = FieldType.Keyword)
    private String categoryName;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Keyword)
    private String productCondition;

    @Field(type = FieldType.Boolean)
    private boolean isDirectDeal;

    @Field(type = FieldType.Text)
    private String directDealLocation;

    @Field(type = FieldType.Boolean)
    private boolean isHide;

    @Field(type = FieldType.Keyword)
    private String status;

    @Field(type = FieldType.Keyword)
    private String thumbnailKey;

    @Field(type = FieldType.Integer)
    private int viewCount;

    @Field(type = FieldType.Integer)
    private int price;

    @Field(type = FieldType.Keyword)
    private String ticketUuid;

    @Field(type = FieldType.Nested)
    private List<ImageUrl> imageUrlList;

    @Field(type = FieldType.Keyword)
    private List<Long> tagIdList;

    @Field(type = FieldType.Keyword)
    private List<String> tagNames;

    @Field(type = FieldType.Boolean)
    private boolean isDeleted;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Instant createdAt;

    @Builder
    public ProductSearchDocument(Long id,
                                 String productUuid,
                                 String saleMemberUuid,
                                 String title,
                                 int categoryId,
                                 String description,
                                 String productCondition,
                                 boolean isDirectDeal,
                                 String directDealLocation,
                                 boolean isHide,
                                 String status,
                                 String thumbnailKey,
                                 int viewCount,
                                 int price,
                                 String ticketUuid,
                                 List<ImageUrl> imageUrlList,
                                 List<Long> tagIdList,
                                 boolean isDeleted,
                                 String categoryName,
                                 List<String> tagNames,
                                 Instant createdAt) {
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
        this.tagNames = tagNames;
        this.categoryName = categoryName;
        this.ticketUuid = ticketUuid;
        this.imageUrlList = imageUrlList;
        this.tagIdList = tagIdList;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }
}
