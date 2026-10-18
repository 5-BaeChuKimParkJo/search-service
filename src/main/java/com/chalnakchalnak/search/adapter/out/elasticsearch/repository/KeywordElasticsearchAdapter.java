package com.chalnakchalnak.search.adapter.out.elasticsearch.repository;

import com.chalnakchalnak.search.adapter.out.elasticsearch.entity.KeywordSearchDocument;
import com.chalnakchalnak.search.adapter.out.elasticsearch.mapper.KeywordSearchDocumentMapper;
import com.chalnakchalnak.search.adapter.out.elasticsearch.querybuilder.KeywordSearchQueryBuilder;
import com.chalnakchalnak.search.application.command.IndexAuctionCommand;
import com.chalnakchalnak.search.application.command.IndexKeywordCommand;
import com.chalnakchalnak.search.application.port.out.KeywordSearchRepositoryPort;
import com.chalnakchalnak.search.application.result.AuctionSuggestionResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class KeywordElasticsearchAdapter implements KeywordSearchRepositoryPort {

  private final KeywordElasticsearchRepository keywordSearchElasticRepository;
  private final KeywordSearchDocumentMapper keywordSearchDocumentMapper;
  private final KeywordSearchQueryBuilder keywordSearchQueryBuilder;
  private final ElasticsearchOperations elasticsearchOperations;

  @Override
  public void saveKeyword(IndexAuctionCommand auctionCreateEventDto) {

    Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);

    Set<String> keywords =
        komoran.analyze(auctionCreateEventDto.getTitle()).getTokenList().stream()
            .filter(token -> token.getPos().equals("NNG") || token.getPos().equals("NNP"))
            .map(Token::getMorph)
            .collect(Collectors.toSet());

    List<KeywordSearchDocument> keywordSearchDocumentList = new ArrayList<>();

    for (String keyword : keywords) {
      KeywordSearchDocument keywordSearchDocument =
          keywordSearchElasticRepository
              .findByKeyword(keyword)
              .orElseGet(() -> KeywordSearchDocument.builder().keyword(keyword).build());

      keywordSearchDocument.incrementWeight();
      keywordSearchDocumentList.add(keywordSearchDocument);
    }

    keywordSearchElasticRepository.saveAll(keywordSearchDocumentList);
  }

  @Override
  public List<AuctionSuggestionResult> suggestAuctionSearch(String keyword) {

    NativeQuery nativeQuery = keywordSearchQueryBuilder.buildKeywordSearchQuery(keyword);

    SearchHits<KeywordSearchDocument> hits =
        elasticsearchOperations.search(nativeQuery, KeywordSearchDocument.class);

    return hits.getSearchHits().stream()
        .map(hit -> keywordSearchDocumentMapper.toSuggestAuctionSearchResponseDto(hit.getContent()))
        .toList();
  }

  @Override
  public void saveKeywordBulk(List<IndexKeywordCommand> keywordBatchEventDtos) {

    Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);

    Map<String, Long> keywordFrequency =
        keywordBatchEventDtos.stream()
            .map(IndexKeywordCommand::getKeyword)
            .flatMap(keyword -> komoran.analyze(keyword).getTokenList().stream())
            .filter(token -> token.getPos().equals("NNG") || token.getPos().equals("NNP"))
            .map(Token::getMorph)
            .collect(Collectors.groupingBy(morph -> morph, Collectors.counting()));

    List<KeywordSearchDocument> keywordSearchDocuments = new ArrayList<>();

    for (Map.Entry<String, Long> entry : keywordFrequency.entrySet()) {
      String keyword = entry.getKey();
      int count = entry.getValue().intValue();

      KeywordSearchDocument document =
          keywordSearchElasticRepository
              .findByKeyword(keyword)
              .orElseGet(() -> KeywordSearchDocument.builder().keyword(keyword).weight(0).build());

      document.incrementWeightBy(count);
      keywordSearchDocuments.add(document);
    }

    keywordSearchElasticRepository.saveAll(keywordSearchDocuments);

    log.info("Saved {} keywords in bulk", keywordSearchDocuments.size());
  }
}
