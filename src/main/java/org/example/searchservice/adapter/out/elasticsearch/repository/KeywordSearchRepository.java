package org.example.searchservice.adapter.out.elasticsearch.repository;


import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.adapter.out.elasticsearch.entity.KeywordSearchDocument;
import org.example.searchservice.adapter.out.elasticsearch.mapper.KeywordSearchDocumentMapper;
import org.example.searchservice.adapter.out.elasticsearch.querybuilder.KeywordSearchQueryBuilder;
import org.example.searchservice.application.dto.in.AuctionCreateEventDto;
import org.example.searchservice.application.dto.in.KeywordBatchEventDto;
import org.example.searchservice.application.dto.out.SuggestAuctionSearchResponseDto;
import org.example.searchservice.application.port.out.KeywordSearchRepositoryPort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class KeywordSearchRepository implements KeywordSearchRepositoryPort {

    private final KeywordSearchElasticRepository keywordSearchElasticRepository;
    private final KeywordSearchDocumentMapper keywordSearchDocumentMapper;
    private final KeywordSearchQueryBuilder keywordSearchQueryBuilder;
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public void saveKeyword(AuctionCreateEventDto auctionCreateEventDto) {

        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);

        Set<String> keywords = komoran.analyze(auctionCreateEventDto.getTitle()).getTokenList()
                .stream()
                .filter(token -> token.getPos().equals("NNG") || token.getPos().equals("NNP"))
                .map(Token::getMorph)
                .collect(Collectors.toSet());

        List<KeywordSearchDocument> keywordSearchDocumentList = new ArrayList<>();

        for (String keyword : keywords) {
            KeywordSearchDocument keywordSearchDocument = keywordSearchElasticRepository.findByKeyword(keyword)
                    .orElseGet(() -> KeywordSearchDocument.builder().keyword(keyword).build());

            keywordSearchDocument.incrementWeight();
            keywordSearchDocumentList.add(keywordSearchDocument);
        }

        keywordSearchElasticRepository.saveAll(keywordSearchDocumentList);

    }

    @Override
    public List<SuggestAuctionSearchResponseDto> suggestAuctionSearch(String keyword) {

        NativeQuery nativeQuery = keywordSearchQueryBuilder
                .buildKeywordSearchQuery(keyword);

        SearchHits<KeywordSearchDocument> hits =
                elasticsearchOperations.search(nativeQuery, KeywordSearchDocument.class);

        return hits.getSearchHits().stream()
                .map(hit -> keywordSearchDocumentMapper.toSuggestAuctionSearchResponseDto(hit.getContent()))
                .toList();
    }

    @Override
    public void saveKeywordBulk(List<KeywordBatchEventDto> keywordBatchEventDtos) {

        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);

        Map<String, Long> keywordFrequency = keywordBatchEventDtos.stream()
                .map(KeywordBatchEventDto::getKeyword)
                .flatMap(keyword -> komoran.analyze(keyword).getTokenList().stream())
                .filter(token -> token.getPos().equals("NNG") || token.getPos().equals("NNP"))
                .map(Token::getMorph)
                .collect(Collectors.groupingBy(morph -> morph, Collectors.counting()));

        List<KeywordSearchDocument> keywordSearchDocuments = new ArrayList<>();

        for (Map.Entry<String, Long> entry : keywordFrequency.entrySet()) {
            String keyword = entry.getKey();
            int count = entry.getValue().intValue();

            KeywordSearchDocument document = keywordSearchElasticRepository.findByKeyword(keyword)
                    .orElseGet(() -> KeywordSearchDocument.builder().keyword(keyword).weight(0).build());

            document.incrementWeightBy(count);
            keywordSearchDocuments.add(document);
        }

        keywordSearchElasticRepository.saveAll(keywordSearchDocuments);

        log.info("Saved {} keywords in bulk", keywordSearchDocuments.size());
    }

}
