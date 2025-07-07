package org.example.searchservice.application.port.out;

import org.example.searchservice.application.dto.in.*;
import org.example.searchservice.application.dto.out.GetAuctionSearchResponseDto;
import org.example.searchservice.application.dto.out.SuggestAuctionSearchResponseDto;

import java.util.List;

public interface AuctionSearchRepositoryPort {

    List<GetAuctionSearchResponseDto> search(GetAuctionSearchRequestDto getAuctionSearchRequestDto);

    void save(CreateAuctionSearchRequestDto createAuctionSearchRequestDto);

    void saveAuction(AuctionCreateEventDto auctionCreateEventDto, CategoryResponseDto categoryResponseDto, List<TagResponseDto> tagResponseDtoList);

    void upsertAuctionBulk(List<AuctionUpsertEventDto> auctionUpsertEventDto);

    void deleteAuctionBulk(List<AuctionDeleteEventDto> auctionDeleteEventDto);
    List<SuggestAuctionSearchResponseDto> suggest(String keyword);




}
