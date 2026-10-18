package com.chalnakchalnak.search.application.port.in;

import com.chalnakchalnak.search.application.result.AuctionSuggestionResult;
import java.util.List;

public interface KeywordSearchUseCase {

  List<AuctionSuggestionResult> suggestKeywords(String keyword);

  void saveKeywordBulk(List<String> titles);

  void saveProductKeywordBulk(List<String> titles);
}
