package org.example.searchservice.adapter.in.vo.out;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SuggestAuctionSearchResponseVo {

    private String keyword;

    @Builder
    public SuggestAuctionSearchResponseVo(String keyword) {
        this.keyword = keyword;
    }

}
