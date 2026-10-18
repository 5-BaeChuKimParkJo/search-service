package com.chalnakchalnak.search.application.port.out;

import com.chalnakchalnak.search.application.command.CreateAuctionIndexCommand;
import com.chalnakchalnak.search.application.command.DeleteAuctionCommand;
import com.chalnakchalnak.search.application.command.IndexAuctionCommand;
import com.chalnakchalnak.search.application.command.UpsertAuctionCommand;
import com.chalnakchalnak.search.application.query.AuctionSearchQuery;
import com.chalnakchalnak.search.application.result.AuctionSearchResult;
import com.chalnakchalnak.search.application.result.AuctionSuggestionResult;
import com.chalnakchalnak.search.application.result.CategoryResult;
import com.chalnakchalnak.search.application.result.TagResult;
import java.util.List;

public interface AuctionSearchRepositoryPort {

  List<AuctionSearchResult> search(AuctionSearchQuery getAuctionSearchRequestDto);

  void save(CreateAuctionIndexCommand createAuctionSearchRequestDto);

  void saveAuction(
      IndexAuctionCommand auctionCreateEventDto,
      CategoryResult categoryResponseDto,
      List<TagResult> tagResponseDtoList);

  void upsertAuctionBulk(List<UpsertAuctionCommand> auctionUpsertEventDto);

  void deleteAuctionBulk(List<DeleteAuctionCommand> auctionDeleteEventDto);

  List<AuctionSuggestionResult> suggest(String keyword);
}
