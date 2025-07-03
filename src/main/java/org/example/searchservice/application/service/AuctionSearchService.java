package org.example.searchservice.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.adapter.in.kafka.event.AuctionBatchEvent;
import org.example.searchservice.adapter.in.kafka.event.AuctionCreateEvent;
import org.example.searchservice.adapter.in.kafka.event.AuctionEvent;
import org.example.searchservice.adapter.in.kafka.mapper.AuctionMessageMapper;
import org.example.searchservice.adapter.out.feign.CategoryClient;
import org.example.searchservice.adapter.out.feign.TagClient;
import org.example.searchservice.application.converter.AuctionBatchEventConverter;
import org.example.searchservice.application.dto.in.*;
import org.example.searchservice.application.dto.out.GetAuctionSearchResponseDto;
import org.example.searchservice.application.dto.out.SuggestAuctionSearchResponseDto;
import org.example.searchservice.application.port.in.AuctionSearchUseCase;
import org.example.searchservice.application.port.out.AuctionSearchRepositoryPort;
import org.example.searchservice.application.port.out.KeywordSearchRepositoryPort;
import org.example.searchservice.common.exception.BaseException;
import org.example.searchservice.common.response.BaseResponseStatus;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionSearchService implements AuctionSearchUseCase {

    private final AuctionSearchRepositoryPort auctionSearchRepositoryPort;
    private final KeywordSearchRepositoryPort keywordSearchRepositoryPort;
    private final AuctionMessageMapper auctionMessageMapper;
    private final AuctionBatchEventConverter auctionBatchEventConverter;
    private final CategoryClient categoryClient;
    private final TagClient tagClient;

    @Override
    public List<GetAuctionSearchResponseDto> searchAuctions(GetAuctionSearchRequestDto getAuctionSearchRequestDto) {

        return auctionSearchRepositoryPort.search(getAuctionSearchRequestDto);
    }

    @Override
    public void createAuctionSearch(CreateAuctionSearchRequestDto createAuctionSearchRequestDto) {
        auctionSearchRepositoryPort.save(createAuctionSearchRequestDto);
    }

    @Override
    public void saveAuctionBulk(List<AuctionEvent> auctionEventList) {

        HashMap<String, AuctionBatchEventDto> map = new HashMap<>();

        auctionEventList.stream()
                        .forEach(auctionEvent -> {

                            AuctionBatchEventDto auctionBatchEventDto = auctionMessageMapper.toAuctionBatchEventDto(auctionEvent.getPayload(), auctionEvent.getOp());

                            log.info("Processing auction event: {}", auctionBatchEventDto.getTitle());

                            map.put(auctionBatchEventDto.getAuctionUuid(), auctionBatchEventDto);
                        });

        log.info("Auction batch event processing completed. Total events: {}", map);

        List<AuctionDeleteEventDto> deleteEventDtos = new ArrayList<>();
        List<AuctionUpsertEventDto> upsertEventDtos = new ArrayList<>();

        map.forEach(
                (uuid, auctionBatchEventDto) -> {
                    if ("d".equals(auctionBatchEventDto.getOp())) {
                        log.info("Adding to delete list: {}", uuid);
                        deleteEventDtos.add(auctionMessageMapper.toAuctionDeleteEventDto(auctionBatchEventDto.getAuctionUuid()));
                    } else {
                        log.info("Adding to upsert list: {}", uuid);

                        CategoryResponseDto categoryResponseDto = null;
                        try {
                            categoryResponseDto = categoryClient.getCategory(auctionBatchEventDto.getCategoryId());
                        } catch (Exception e) {
                            log.info("Failed to fetch category with ID: {}", auctionBatchEventDto.getCategoryId());
                            throw new BaseException(BaseResponseStatus.FAILED_TO_FEIGHN_CATEGORY);
                        }

                        List<TagResponseDto> tagResponseDtoList = new ArrayList<>();
                        auctionBatchEventDto.getTagIds().stream()
                                .forEach(tagId -> {
                                    try {
                                        TagResponseDto tagResponseDto = tagClient.getTagById(tagId);
                                        tagResponseDtoList.add(tagResponseDto);

                                    } catch (Exception e) {
                                        log.info("Failed to fetch tag with ID: {}", tagId);
                                        throw new BaseException(BaseResponseStatus.FAILED_TO_FEIGHN_TAG);
                                    }
                                });

                        AuctionUpsertEventDto auctionUpsertEventDto = auctionBatchEventConverter
                                .toAuctionUpsertEventDto(auctionBatchEventDto, categoryResponseDto, tagResponseDtoList);

                        upsertEventDtos.add(auctionUpsertEventDto);
                    }
                }
        );

        log.info("Delete event DTOs: {}", deleteEventDtos.stream().map(AuctionDeleteEventDto::getAuctionUuid).toList());
        log.info("Upsert event DTOs: {}", upsertEventDtos.stream().map(AuctionUpsertEventDto::getAuctionUuid).toList());

        auctionSearchRepositoryPort.upsertAuctionBulk(upsertEventDtos);
        auctionSearchRepositoryPort.deleteAuctionBulk(deleteEventDtos);

    }

    @Override
    public List<SuggestAuctionSearchResponseDto> suggestAuctions(String keyword) {

        return keywordSearchRepositoryPort.suggestAuctionSearch(keyword);

    }

    @Override
    public void saveAuction(AuctionCreateEventDto auctionCreateEventDto) {

        CategoryResponseDto categoryResponseDto = null;
        try {
            categoryResponseDto = categoryClient.getCategory(auctionCreateEventDto.getCategoryId());
        } catch (Exception e) {
            log.info("Failed to fetch category with ID: {}", auctionCreateEventDto.getCategoryId());
            throw new BaseException(BaseResponseStatus.FAILED_TO_FEIGHN_CATEGORY);
        }

        List<TagResponseDto> tagResponseDtoList = new ArrayList<>();

        auctionCreateEventDto.getTagIds().stream()
                .forEach( tagId -> {
                    try {
                        TagResponseDto tagResponseDto = tagClient.getTagById(tagId);
                        tagResponseDtoList.add(tagResponseDto);
                    } catch (Exception e) {
                        log.info("Failed to fetch tag with ID: {}", tagId);
                        throw new BaseException(BaseResponseStatus.FAILED_TO_FEIGHN_TAG);
                    }
                });

        auctionSearchRepositoryPort.saveAuction(auctionCreateEventDto, categoryResponseDto, tagResponseDtoList);
        keywordSearchRepositoryPort.saveKeyword(auctionCreateEventDto);

    }


}
