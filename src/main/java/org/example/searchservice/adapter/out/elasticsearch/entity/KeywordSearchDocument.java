package org.example.searchservice.adapter.out.elasticsearch.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "keyword_search")
@NoArgsConstructor
@Getter
public class KeywordSearchDocument {

    @Id
    @Field(type = FieldType.Text)
    private String keyword;

    @Field(type = FieldType.Integer)
    private int weight = 0;

    @Builder
    public KeywordSearchDocument(String keyword, int weight) {
        this.keyword = keyword;
        this.weight = weight;
    }

    public void incrementWeight() {
        this.weight++;
    }

    public void incrementWeightBy(int increment) {
        this.weight += increment;
    }
}
