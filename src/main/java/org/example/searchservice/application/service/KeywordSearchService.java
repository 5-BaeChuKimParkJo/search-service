package org.example.searchservice.application.service;


import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import org.example.searchservice.adapter.in.kafka.event.auction.AuctionEvent;
import org.example.searchservice.adapter.in.kafka.event.KeywordBatchEvent;
import org.example.searchservice.adapter.in.kafka.event.product.ProductBatchEvent;
import org.example.searchservice.adapter.in.kafka.event.product.ProductEvent;
import org.example.searchservice.adapter.in.kafka.mapper.KeywordMessageMapper;
import org.example.searchservice.application.dto.in.KeywordBatchEventDto;
import org.example.searchservice.application.dto.out.SuggestAuctionSearchResponseDto;
import org.example.searchservice.application.port.in.KeywordSearchUseCase;
import org.example.searchservice.application.port.out.KeywordSearchRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordSearchService implements KeywordSearchUseCase {

    private final KeywordMessageMapper keywordMessageMapper;
    private final KeywordSearchRepositoryPort keywordSearchRepositoryPort;
    @Override
    public List<SuggestAuctionSearchResponseDto> suggestKeywords(String keyword) {
        return null;
    }

    @Override
    public void saveKeywordBulk(List<AuctionEvent> auctionEventList) {

        List<KeywordBatchEvent> keywordBatchEvents = auctionEventList.stream()
                .map(keywordMessageMapper::toKeywordBatchEvent)
                .toList();

        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);

        Map<String, Integer> keywordFrequencyMap = keywordBatchEvents.stream()
                .map(KeywordBatchEvent::getTitle)
                .map(komoran::analyze)
                .flatMap(result -> result.getTokenList().stream())
                .filter(token -> token.getPos().equals("NNG") || token.getPos().equals("NNP"))
                .map(Token::getMorph)
                .collect(Collectors.toMap(
                        morph -> morph,
                        morph -> 1,
                        Integer::sum // 중복 키워드는 개수 누적
                ));

        List<KeywordBatchEventDto> keywordBatchEventDtos = keywordFrequencyMap.entrySet().stream()
                .map(entry -> {
                    String keyword = entry.getKey();
                    int weight = entry.getValue();
                    return keywordMessageMapper.toKeywordBatchEventDto(keyword, weight); // 수동으로 DTO 가중치 넣기
                })
                .toList();

        keywordSearchRepositoryPort.saveKeywordBulk(keywordBatchEventDtos);


    }

    @Override
    public void saveProductKeywordBulk(List<ProductEvent> productEvents) {

        List<ProductBatchEvent> productBatchEvents = productEvents.stream()
                .filter(Objects::nonNull)
                .map(keywordMessageMapper::toProductBatchEvent)
                .toList();

        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);

        Map<String, Integer> keywordFrequencyMap = productBatchEvents.stream()
                .map(ProductBatchEvent::getTitle)
                .map(komoran::analyze)
                .flatMap(result -> result.getTokenList().stream())
                .filter(token -> token.getPos().equals("NNG") || token.getPos().equals("NNP"))
                .map(Token::getMorph)
                .collect(Collectors.toMap(
                        morph -> morph,
                        morph -> 1,
                        Integer::sum // 중복 키워드는 개수 누적
                ));

        List<KeywordBatchEventDto> keywordBatchEventDtos = keywordFrequencyMap.entrySet().stream()
                .map(entry -> {
                    String keyword = entry.getKey();
                    int weight = entry.getValue();
                    return keywordMessageMapper.toKeywordBatchEventDto(keyword, weight); // 수동으로 DTO 가중치 넣기
                })
                .toList();

        keywordSearchRepositoryPort.saveKeywordBulk(keywordBatchEventDtos);


    }
}
