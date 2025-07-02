package org.example.searchservice.adapter.in.vo.out;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetAuctionSearchResponseWrapperVo {

    private List<GetAuctionSearchResponseVo> getAuctionSearchResponseVoList;
    private NextCursorVo nextCursorVo;

    @Builder
    public GetAuctionSearchResponseWrapperVo(List<GetAuctionSearchResponseVo> getAuctionSearchResponseVoList, NextCursorVo nextCursorVo) {
        this.getAuctionSearchResponseVoList = getAuctionSearchResponseVoList;
        this.nextCursorVo = nextCursorVo;
    }
}
