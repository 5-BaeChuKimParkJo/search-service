package org.example.searchservice.application.port.out;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.example.searchservice.adapter.out.elasticsearch.entity.AuctionSearchDocument;
import org.example.searchservice.application.dto.in.*;
import org.example.searchservice.application.dto.out.GetAuctionSearchResponseDto;

import java.util.List;

public interface AuctionSearchRepositoryPort {

    List<GetAuctionSearchResponseDto> search(GetAuctionSearchRequestDto getAuctionSearchRequestDto);

    void save(CreateAuctionSearchRequestDto createAuctionSearchRequestDto);

    void saveMessage(String message);

    void saveAuction(AuctionCreateEventDto auctionCreateEventDto, CategoryResponseDto categoryResponseDto, List<TagResponseDto> tagResponseDtoList);

}
