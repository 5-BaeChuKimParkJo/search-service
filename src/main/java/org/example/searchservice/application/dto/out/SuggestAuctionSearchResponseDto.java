package org.example.searchservice.application.dto.out;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SuggestAuctionSearchResponseDto {

    private String keyword;
    private int weight;

    @Builder
    public SuggestAuctionSearchResponseDto(String keyword, int weight) {
        this.keyword = keyword;
        this.weight = weight;
    }
}
