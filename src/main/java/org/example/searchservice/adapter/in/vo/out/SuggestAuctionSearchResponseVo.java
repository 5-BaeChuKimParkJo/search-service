package org.example.searchservice.adapter.in.vo.out;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SuggestAuctionSearchResponseVo {

    private String keyword;
    private int weight;

    @Builder
    public SuggestAuctionSearchResponseVo(String keyword, int weight) {
        this.keyword = keyword;
        this.weight = weight;
    }

}
