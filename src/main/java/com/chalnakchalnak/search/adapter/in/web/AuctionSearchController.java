package com.chalnakchalnak.search.adapter.in.web;

import com.chalnakchalnak.search.adapter.in.web.mapper.AuctionSearchWebMapper;
import com.chalnakchalnak.search.adapter.in.web.request.AuctionSearchRequest;
import com.chalnakchalnak.search.adapter.in.web.request.CreateAuctionIndexRequest;
import com.chalnakchalnak.search.adapter.in.web.response.AuctionSearchPageResponse;
import com.chalnakchalnak.search.adapter.in.web.response.AuctionSuggestionResponse;
import com.chalnakchalnak.search.application.port.in.AuctionSearchUseCase;
import com.chalnakchalnak.search.application.result.AuctionSearchResult;
import com.chalnakchalnak.search.application.result.AuctionSuggestionResult;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/search-service/api/v1/auction/search")
@RequiredArgsConstructor
public class AuctionSearchController {

  private final AuctionSearchUseCase auctionSearchUseCase;
  private final AuctionSearchWebMapper auctionSearchVoMapper;

  @Operation(
      summary =
          "Search for auctions based on various criteria: sortBy: latest, priceHigh, priceLow, recommended")
  @GetMapping
  public AuctionSearchPageResponse getAuctionSearchList(
      @ModelAttribute AuctionSearchRequest getAuctionSearchRequestVo) {

    log.info(
        "Received request to search auctions with parameters: {}",
        getAuctionSearchRequestVo.getAuctionTitle());
    List<AuctionSearchResult> getAuctionSearchResponseDtoList =
        auctionSearchUseCase.searchAuctions(
            auctionSearchVoMapper.toGetAuctionSearchRequestDto(getAuctionSearchRequestVo));

    return auctionSearchVoMapper.toGetAuctionSearchResponseWrapperVo(
        getAuctionSearchResponseDtoList);
  }

  @Operation(summary = "Create a new auction search, only for TEST, 안씀")
  @PostMapping
  public void createAuctionSearch(
      @RequestBody CreateAuctionIndexRequest createAuctionSearchRequestVo) {
    auctionSearchUseCase.createAuctionSearch(
        auctionSearchVoMapper.toCreateAuctionSearchRequestDto(createAuctionSearchRequestVo));
  }

  @Operation(summary = "Suggest auctions based on a keyword, 안쓸 예정")
  @GetMapping("/suggest")
  public List<AuctionSuggestionResponse> suggestAuctionSearch(@RequestParam String keyword) {
    log.info("Received request to suggest auctions with title: {}", keyword);
    List<AuctionSuggestionResult> suggestedAuctions = auctionSearchUseCase.suggestAuctions(keyword);

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
