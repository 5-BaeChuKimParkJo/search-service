package org.example.searchservice.adapter.out.elasticsearch.entity;


import lombok.*;
import org.example.searchservice.adapter.in.kafka.event.product.ImageUrl;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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

    @Field(type = FieldType.Text, analyzer = "korean") // 또는 "standard"
    private String title;

    @Field(type = FieldType.Keyword)
    private String categoryId;

    @Field(type = FieldType.Text, analyzer = "korean")
    private String description;

    @Field(type = FieldType.Keyword)
    private String productCondition;

    @Field(type = FieldType.Boolean)
    private Boolean isDirectDeal;

    @Field(type = FieldType.Text, analyzer = "korean")
    private String directDealLocation;

    @Field(type = FieldType.Boolean)
    private Boolean isHide;

    @Field(type = FieldType.Keyword)
    private String status;

    @Field(type = FieldType.Keyword)
    private String thumbnailKey;

    @Field(type = FieldType.Integer)
    private Integer viewCount;

    @Field(type = FieldType.Integer)
    private Integer price;

    @Field(type = FieldType.Keyword)
    private String ticketUuid;

    @Field(type = FieldType.Nested)
    private List<ImageUrl> imageUrlList;

    @Field(type = FieldType.Keyword)
    private List<String> tagIdList;

    @Field(type = FieldType.Boolean)
    private Boolean isDeleted;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private LocalDateTime createdAt;

    @Builder
    public ProductSearchDocument(Long id, String productUuid, String saleMemberUuid, String title, String categoryId,
                                 String description, String productCondition, Boolean isDirectDeal,
                                 String directDealLocation, Boolean isHide, String status, String thumbnailKey,
                                 Integer viewCount, Integer price, String ticketUuid, List<ImageUrl> imageUrlList,
                                 List<String> tagIdList, Boolean isDeleted, LocalDateTime createdAt) {
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
