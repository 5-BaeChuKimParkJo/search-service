package org.example.searchservice.application.dto.in;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponseDto {

    @JsonProperty("categoryId")
    private int categoryId;
    @JsonProperty("name")
    private String categoryName;
    @JsonProperty("description")
    private String categoryDescription;
    @JsonProperty("imageUrl")
    private String categoryImageUrl;

    @Builder
    public CategoryResponseDto(int categoryId, String categoryName, String categoryDescription, String categoryImageUrl) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryImageUrl = categoryImageUrl;
    }


}
