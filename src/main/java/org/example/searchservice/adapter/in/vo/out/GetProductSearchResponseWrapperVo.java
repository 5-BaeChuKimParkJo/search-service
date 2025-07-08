package org.example.searchservice.adapter.in.vo.out;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetProductSearchResponseWrapperVo {

    List<GetProductSearchResponseVo> getProductSearchResponseVoList;
    NextCursorProductVo nextCursorVo;

    @Builder
    public GetProductSearchResponseWrapperVo(List<GetProductSearchResponseVo> getProductSearchResponseVoList, NextCursorProductVo nextCursorVo) {
        this.getProductSearchResponseVoList = getProductSearchResponseVoList;
        this.nextCursorVo = nextCursorVo;
    }
}
