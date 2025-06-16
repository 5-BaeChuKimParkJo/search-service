package org.example.searchservice.adapter.in.web.presentation;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.adapter.in.mapper.AuctionSearchVoMapper;
import org.example.searchservice.adapter.in.vo.in.CreateAuctionSearchRequestVo;
import org.example.searchservice.adapter.in.vo.in.GetAuctionSearchRequestVo;
import org.example.searchservice.adapter.in.vo.out.SuggestAuctionSearchResponseVo;
import org.example.searchservice.adapter.in.vo.out.GetAuctionSearchResponseVo;
import org.example.searchservice.application.dto.out.GetAuctionSearchResponseDto;
import org.example.searchservice.application.dto.out.SuggestAuctionSearchResponseDto;
import org.example.searchservice.application.port.in.AuctionSearchUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/search-service/api/v1/auction/search")
@RequiredArgsConstructor
public class AuctionSearchController {

    private final AuctionSearchUseCase auctionSearchUseCase;
    private final AuctionSearchVoMapper auctionSearchVoMapper;

    @GetMapping
    public List<GetAuctionSearchResponseVo> getAuctionSearchList(
            @ModelAttribute GetAuctionSearchRequestVo getAuctionSearchRequestVo
    ){

        log.info("Received request to search auctions with parameters: {}", getAuctionSearchRequestVo.getTitle());
        List<GetAuctionSearchResponseDto> getAuctionSearchResponseDto = auctionSearchUseCase.searchAuctions(
                auctionSearchVoMapper.toGetAuctionSearchRequestDto(getAuctionSearchRequestVo)
        );

        return getAuctionSearchResponseDto.stream()
                .map(auctionSearchVoMapper::toGetAuctionSearchResponseVo)
                .toList();
    }

    @PostMapping
    public void createAuctionSearch(
            @RequestBody CreateAuctionSearchRequestVo createAuctionSearchRequestVo
            ){
        auctionSearchUseCase.createAuctionSearch(
                auctionSearchVoMapper.toCreateAuctionSearchRequestDto(createAuctionSearchRequestVo)
        );
    }

    @GetMapping("/suggest")
    public List<SuggestAuctionSearchResponseVo> suggestAuctionSearch(
            @RequestParam String keyword
    ) {
        log.info("Received request to suggest auctions with title: {}", keyword);
        List<SuggestAuctionSearchResponseDto> suggestedAuctions = auctionSearchUseCase.suggestAuctions(keyword);

        return suggestedAuctions.stream()
                .map(auctionSearchVoMapper::toSuggestAuctionSearchResponseVo)
                .toList();
    }


}
