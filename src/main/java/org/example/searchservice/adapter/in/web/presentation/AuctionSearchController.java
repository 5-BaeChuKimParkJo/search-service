package org.example.searchservice.adapter.in.web.presentation;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.adapter.in.mapper.AuctionSearchVoMapper;
import org.example.searchservice.adapter.in.vo.in.CreateAuctionSearchRequestVo;
import org.example.searchservice.adapter.in.vo.in.GetAuctionSearchRequestVo;
import org.example.searchservice.adapter.in.vo.out.GetAuctionSearchResponseWrapperVo;
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


    @Operation(summary = "Search for auctions based on various criteria: sortBy: latest, priceHigh, priceLow, recommended")
    @GetMapping
    public GetAuctionSearchResponseWrapperVo getAuctionSearchList(
            @ModelAttribute GetAuctionSearchRequestVo getAuctionSearchRequestVo
    ){

        log.info("Received request to search auctions with parameters: {}", getAuctionSearchRequestVo.getAuctionTitle());
        List<GetAuctionSearchResponseDto> getAuctionSearchResponseDtoList = auctionSearchUseCase.searchAuctions(
                auctionSearchVoMapper.toGetAuctionSearchRequestDto(getAuctionSearchRequestVo)
        );

        return auctionSearchVoMapper.toGetAuctionSearchResponseWrapperVo(
                getAuctionSearchResponseDtoList
        );
    }

    @Operation(summary = "Create a new auction search, only for TEST, 안씀")
    @PostMapping
    public void createAuctionSearch(
            @RequestBody CreateAuctionSearchRequestVo createAuctionSearchRequestVo
            ){
        auctionSearchUseCase.createAuctionSearch(
                auctionSearchVoMapper.toCreateAuctionSearchRequestDto(createAuctionSearchRequestVo)
        );
    }

    @Operation(summary = "Suggest auctions based on a keyword, 안쓸 예정")
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

    @Operation(summary = "Test endpoint to check if the service is running")
    @GetMapping("/test")
    public String test() {
        return "Search Service is running successfully!";
    }


}
