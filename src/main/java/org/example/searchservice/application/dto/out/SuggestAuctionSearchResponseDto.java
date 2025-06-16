package org.example.searchservice.application.dto.out;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SuggestAuctionSearchResponseDto {

    private String keyword;

    @Builder
    public SuggestAuctionSearchResponseDto(String keyword) {
        this.keyword = keyword;
    }
}
