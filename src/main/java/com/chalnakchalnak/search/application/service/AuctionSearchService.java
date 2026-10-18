package com.chalnakchalnak.search.application.service;

import com.chalnakchalnak.search.application.command.AuctionIndexCommand;
import com.chalnakchalnak.search.application.command.CreateAuctionIndexCommand;
import com.chalnakchalnak.search.application.command.DeleteAuctionCommand;
import com.chalnakchalnak.search.application.command.IndexAuctionCommand;
import com.chalnakchalnak.search.application.command.UpsertAuctionCommand;
import com.chalnakchalnak.search.application.converter.AuctionBatchEventConverter;
import com.chalnakchalnak.search.application.port.in.AuctionSearchUseCase;
import com.chalnakchalnak.search.application.port.out.AuctionSearchRepositoryPort;
import com.chalnakchalnak.search.application.port.out.KeywordSearchRepositoryPort;
import com.chalnakchalnak.search.application.port.out.TaxonomyLookupPort;
import com.chalnakchalnak.search.application.query.AuctionSearchQuery;
import com.chalnakchalnak.search.application.result.AuctionSearchResult;
import com.chalnakchalnak.search.application.result.AuctionSuggestionResult;
import com.chalnakchalnak.search.application.result.CategoryResult;
import com.chalnakchalnak.search.application.result.TagResult;
import com.chalnakchalnak.search.common.exception.BaseException;
import com.chalnakchalnak.search.common.response.BaseResponseStatus;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionSearchService implements AuctionSearchUseCase {
  private final AuctionSearchRepositoryPort auctions;
  private final KeywordSearchRepositoryPort keywords;
  private final AuctionBatchEventConverter converter;
  private final TaxonomyLookupPort taxonomy;

  @Override
  public List<AuctionSearchResult> searchAuctions(AuctionSearchQuery query) {
    return auctions.search(query);
  }

  @Override
  public void createAuctionSearch(CreateAuctionIndexCommand command) {
    auctions.save(command);
  }

  @Override
  public List<AuctionSuggestionResult> suggestAuctions(String keyword) {
    return keywords.suggestAuctionSearch(keyword);
  }

  @Override
  public void saveAuctionBulk(List<AuctionIndexCommand> commands) {
    Map<String, AuctionIndexCommand> latest = new LinkedHashMap<>();
    commands.forEach(command -> latest.put(command.getAuctionUuid(), command));
    List<DeleteAuctionCommand> deletes = new ArrayList<>();
    List<UpsertAuctionCommand> upserts = new ArrayList<>();
    latest.forEach(
        (uuid, command) -> {
          if ("d".equals(command.getOp())) {
            deletes.add(DeleteAuctionCommand.builder().auctionUuid(uuid).build());
          } else {
            upserts.add(converter.toAuctionUpsertEventDto(command));
          }
        });
    auctions.upsertAuctionBulk(upserts);
    auctions.deleteAuctionBulk(deletes);
  }

  @Override
  public void saveAuction(IndexAuctionCommand command) {
    CategoryResult category;
    try {
      category = taxonomy.getCategory(command.getCategoryId());
    } catch (Exception error) {
      throw new BaseException(BaseResponseStatus.FAILED_TO_FEIGHN_CATEGORY);
    }
    List<TagResult> tags = new ArrayList<>();
    for (Long id : command.getTagIds()) {
      try {
        tags.add(taxonomy.getTagById(id));
      } catch (Exception error) {
        throw new BaseException(BaseResponseStatus.FAILED_TO_FEIGHN_TAG);
      }
    }
    auctions.saveAuction(command, category, tags);
    keywords.saveKeyword(command);
  }
}
