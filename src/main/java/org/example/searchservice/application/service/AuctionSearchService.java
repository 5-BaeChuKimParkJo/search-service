package org.example.searchservice.application.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.adapter.out.elasticsearch.entity.AuctionSearchDocument;
import org.example.searchservice.adapter.out.elasticsearch.querybuilder.AuctionSearchQueryBuilder;
import org.example.searchservice.adapter.out.feign.CategoryClient;
import org.example.searchservice.adapter.out.feign.TagClient;
import org.example.searchservice.application.dto.in.*;
import org.example.searchservice.application.dto.out.GetAuctionSearchResponseDto;
import org.example.searchservice.application.dto.out.SuggestAuctionSearchResponseDto;
import org.example.searchservice.application.port.in.AuctionSearchUseCase;
import org.example.searchservice.application.port.out.AuctionSearchRepositoryPort;
import org.example.searchservice.application.port.out.KeywordSearchRepositoryPort;
import org.example.searchservice.common.exception.BaseException;
import org.example.searchservice.common.response.BaseResponseStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionSearchService implements AuctionSearchUseCase {

    private final AuctionSearchRepositoryPort auctionSearchRepositoryPort;
    private final KeywordSearchRepositoryPort keywordSearchRepositoryPort;
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
