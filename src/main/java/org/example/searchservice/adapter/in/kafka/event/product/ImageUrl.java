package org.example.searchservice.adapter.in.kafka.event.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageUrl {
    @Field(type = FieldType.Integer)
    private Integer productImageId;

    @Field(type = FieldType.Keyword)
    private String url;

    @Field(type = FieldType.Integer)
    private Integer order;
}