package org.example.searchservice.application.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagResponseDto {

    @JsonProperty("tagId")
    private Long tagId;
    @JsonProperty("name")
    private String tagName;

    @Builder
    public TagResponseDto(Long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

}
