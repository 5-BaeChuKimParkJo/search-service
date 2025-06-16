package org.example.searchservice.application.port.in;

import org.example.searchservice.application.dto.in.CreateAuctionSearchRequestDto;
import org.example.searchservice.application.dto.in.GetAuctionSearchRequestDto;
import org.example.searchservice.application.dto.out.GetAuctionSearchResponseDto;
import org.example.searchservice.application.dto.out.SuggestAuctionSearchResponseDto;

import java.util.List;


public interface AuctionSearchUseCase {

    List<GetAuctionSearchResponseDto> searchAuctions(GetAuctionSearchRequestDto getAuctionSearchRequestDto);
    void createAuctionSearch(CreateAuctionSearchRequestDto createAuctionSearchRequestDto);

    List<SuggestAuctionSearchResponseDto> suggestAuctions(String keyword);



}
