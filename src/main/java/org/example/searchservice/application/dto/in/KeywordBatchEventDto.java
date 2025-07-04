package org.example.searchservice.application.dto.in;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KeywordBatchEventDto {

    private String keyword;
    private int weight;

    @Builder
    public KeywordBatchEventDto(String keyword, int weight) {
        this.keyword = keyword;
        this.weight = weight;
    }
}
