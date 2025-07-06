package org.example.searchservice.application.port.in;

import org.example.searchservice.adapter.in.kafka.event.AuctionCreateEvent;
import org.example.searchservice.adapter.in.kafka.event.AuctionEvent;
import org.example.searchservice.application.dto.in.AuctionCreateEventDto;
import org.example.searchservice.application.dto.in.CreateAuctionSearchRequestDto;
import org.example.searchservice.application.dto.in.GetAuctionSearchRequestDto;
import org.example.searchservice.application.dto.out.GetAuctionSearchResponseDto;
import org.example.searchservice.application.dto.out.SuggestAuctionSearchResponseDto;

import java.util.List;


public interface AuctionSearchUseCase {

    List<GetAuctionSearchResponseDto> searchAuctions(GetAuctionSearchRequestDto getAuctionSearchRequestDto);
    void createAuctionSearch(CreateAuctionSearchRequestDto createAuctionSearchRequestDto);

    void saveAuctionBulk(List<AuctionEvent> auctionEventList);

    List<SuggestAuctionSearchResponseDto> suggestAuctions(String keyword);

    void saveAuction(AuctionCreateEventDto auctionCreateEventDto);


}
