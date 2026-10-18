package com.chalnakchalnak.search.application.port.out;

import com.chalnakchalnak.search.application.command.IndexAuctionCommand;
import com.chalnakchalnak.search.application.command.IndexKeywordCommand;
import com.chalnakchalnak.search.application.result.AuctionSuggestionResult;
import java.util.List;

public interface KeywordSearchRepositoryPort {

  void saveKeyword(IndexAuctionCommand auctionCreateEventDto);

  List<AuctionSuggestionResult> suggestAuctionSearch(String keyword);

  void saveKeywordBulk(List<IndexKeywordCommand> keywordBatchEventDtos);
}
