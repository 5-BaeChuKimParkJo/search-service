package org.example.searchservice.adapter.out.elasticsearch.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "option_search")
@Getter
@NoArgsConstructor
public class OptionSearchDocument {

    //category
    private Long categoryId;
    private String categoryName;
    private String categoryDescription;
    private String categoryThumbnailKey;
    //tag
    private Long tagId;
    private String tagNames;

}
