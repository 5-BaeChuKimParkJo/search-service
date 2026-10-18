package com.chalnakchalnak.search.application.port.in;

import com.chalnakchalnak.search.application.command.AuctionIndexCommand;
import com.chalnakchalnak.search.application.command.CreateAuctionIndexCommand;
import com.chalnakchalnak.search.application.command.IndexAuctionCommand;
import com.chalnakchalnak.search.application.query.AuctionSearchQuery;
import com.chalnakchalnak.search.application.result.AuctionSearchResult;
import com.chalnakchalnak.search.application.result.AuctionSuggestionResult;
import java.util.List;

public interface AuctionSearchUseCase {

  List<AuctionSearchResult> searchAuctions(AuctionSearchQuery getAuctionSearchRequestDto);

  void createAuctionSearch(CreateAuctionIndexCommand createAuctionSearchRequestDto);

  void saveAuctionBulk(List<AuctionIndexCommand> commands);

  List<AuctionSuggestionResult> suggestAuctions(String keyword);

  void saveAuction(IndexAuctionCommand auctionCreateEventDto);
}
