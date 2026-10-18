package com.chalnakchalnak.search.application.service;

import com.chalnakchalnak.search.application.command.IndexKeywordCommand;
import com.chalnakchalnak.search.application.port.in.KeywordSearchUseCase;
import com.chalnakchalnak.search.application.port.out.KeywordSearchRepositoryPort;
import com.chalnakchalnak.search.application.result.AuctionSuggestionResult;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordSearchService implements KeywordSearchUseCase {
  private final KeywordSearchRepositoryPort keywords;

  @Override
  public List<AuctionSuggestionResult> suggestKeywords(String keyword) {
    return null;
  }

  @Override
  public void saveKeywordBulk(List<String> titles) {
    indexTitles(titles);
  }

  @Override
  public void saveProductKeywordBulk(List<String> titles) {
    indexTitles(titles);
  }

  private void indexTitles(List<String> titles) {
    Komoran analyzer = new Komoran(DEFAULT_MODEL.FULL);
    Map<String, Integer> frequency =
        titles.stream()
            .map(analyzer::analyze)
            .flatMap(result -> result.getTokenList().stream())
            .filter(token -> token.getPos().equals("NNG") || token.getPos().equals("NNP"))
            .map(Token::getMorph)
            .collect(Collectors.toMap(word -> word, word -> 1, Integer::sum));
    keywords.saveKeywordBulk(
        frequency.entrySet().stream()
            .map(
                entry ->
                    IndexKeywordCommand.builder()
                        .keyword(entry.getKey())
                        .weight(entry.getValue())
                        .build())
            .toList());
  }
}
