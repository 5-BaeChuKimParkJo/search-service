package com.chalnakchalnak.search.adapter.out.elasticsearch.repository;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import com.chalnakchalnak.search.adapter.out.elasticsearch.entity.AuctionSearchDocument;
import com.chalnakchalnak.search.adapter.out.elasticsearch.mapper.AuctionSearchDocumentMapper;
import com.chalnakchalnak.search.adapter.out.elasticsearch.querybuilder.AuctionSearchQueryBuilder;
import com.chalnakchalnak.search.application.command.CreateAuctionIndexCommand;
import com.chalnakchalnak.search.application.command.DeleteAuctionCommand;
import com.chalnakchalnak.search.application.command.IndexAuctionCommand;
import com.chalnakchalnak.search.application.command.UpsertAuctionCommand;
import com.chalnakchalnak.search.application.port.out.AuctionSearchRepositoryPort;
import com.chalnakchalnak.search.application.query.AuctionSearchQuery;
import com.chalnakchalnak.search.application.result.AuctionSearchResult;
import com.chalnakchalnak.search.application.result.AuctionSuggestionResult;
import com.chalnakchalnak.search.application.result.CategoryResult;
import com.chalnakchalnak.search.application.result.TagResult;
import com.chalnakchalnak.search.common.exception.BaseException;
import com.chalnakchalnak.search.common.response.BaseResponseStatus;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AuctionElasticsearchAdapter implements AuctionSearchRepositoryPort {

  private final AuctionElasticsearchRepository auctionSearchElasticRepository;
  private final AuctionSearchDocumentMapper auctionSearchDocumentMapper;
  private final ElasticsearchOperations elasticsearchOperations;
  private final AuctionSearchQueryBuilder auctionSearchQueryBuilder;

  @Override
  public List<AuctionSearchResult> search(AuctionSearchQuery getAuctionSearchRequestDto) {

    NativeQuery nativeQuery =
        auctionSearchQueryBuilder.buildAuctionsSearchQuery(getAuctionSearchRequestDto);

    log.info("Executing search with query: {}", nativeQuery.getQuery());
    log.info("Search Sort {}", nativeQuery.getSort());
    log.info("Search After {}", nativeQuery.getSearchAfter());

    try {
      SearchHits<AuctionSearchDocument> hits =
          elasticsearchOperations.search(nativeQuery, AuctionSearchDocument.class);

      return hits.getSearchHits().stream()
          .map(hit -> auctionSearchDocumentMapper.toGetAuctionSearchResponseDto(hit.getContent()))
          .toList();

    } catch (ElasticsearchException | UncategorizedElasticsearchException e) {
      log.error("Elasticsearch error occurred: {}", e.getMessage(), e);
      throw new BaseException(BaseResponseStatus.FAILED_AUCTION_SEARCH);
    }
  }

  @Override
  public void save(CreateAuctionIndexCommand createAuctionSearchRequestDto) {
    try {
      auctionSearchElasticRepository.save(
          auctionSearchDocumentMapper.toAuctionSearchDocument(createAuctionSearchRequestDto));
    } catch (Exception e) {
      throw new BaseException(BaseResponseStatus.FAILED_AUCTION_SAVE);
    }
  }

  @Override
  public void saveAuction(
      IndexAuctionCommand auctionCreateEventDto,
      CategoryResult categoryResponseDto,
      List<TagResult> tagResponseDtoList) {

    try {
      auctionSearchElasticRepository.save(
          auctionSearchDocumentMapper.toAuctionSearchDocument(
              auctionCreateEventDto, categoryResponseDto, tagResponseDtoList));
      log.info("Auction with UUID {} saved successfully.", auctionCreateEventDto.getAuctionUuid());
      log.info("Category: {}", auctionCreateEventDto.getTitle());
    } catch (Exception e) {
      throw new BaseException(BaseResponseStatus.FAILED_AUCTION_SAVE);
    }
  }

  @Override
  public void upsertAuctionBulk(List<UpsertAuctionCommand> auctionUpsertEventDto) {

    List<AuctionSearchDocument> auctionSearchDocuments =
        auctionUpsertEventDto.stream()
            .map(auctionSearchDocumentMapper::toAuctionSearchDocument)
            .toList();

    try {
      auctionSearchElasticRepository.saveAll(auctionSearchDocuments);
      log.info("Bulk upsert of auctions completed successfully.");
    } catch (Exception e) {
      log.error("Error during bulk upsert of auctions: {}", e.getMessage(), e);
      throw new BaseException(BaseResponseStatus.FAILED_AUCTION_BULK_UPSERT);
    }
  }

  @Override
  public void deleteAuctionBulk(List<DeleteAuctionCommand> auctionDeleteEventDto) {

    List<String> auctionUuids =
        auctionDeleteEventDto.stream().map(DeleteAuctionCommand::getAuctionUuid).toList();

    List<String> auctionUuIds = new ArrayList<>();

    auctionUuids.stream()
        .forEach(
            uuid -> {
              try {
                auctionSearchElasticRepository
                    .findByAuctionUuid(uuid)
                    .ifPresent(
                        auctionSearchDocument -> {
                          auctionUuIds.add(auctionSearchDocument.getAuctionUuid());
                          log.info("Found auction with UUID: {}", uuid);
                        });
              } catch (Exception e) {
                log.error("Error finding auction with UUID {}: {}", uuid, e.getMessage(), e);
                throw new BaseException(BaseResponseStatus.FAILED_AUCTION_BULK_DELETE);
              }
            });

    auctionUuIds.stream()
        .forEach(
            id -> {
              try {
                auctionSearchElasticRepository.deleteById(id);
              } catch (Exception e) {
                log.error("Error deleting auction with ID {}: {}", id, e.getMessage(), e);
                throw new BaseException(BaseResponseStatus.FAILED_AUCTION_BULK_DELETE);
              }
            });
  }

  @Override
  public List<AuctionSuggestionResult> suggest(String keyword) {

    NativeQuery nativeQuery = auctionSearchQueryBuilder.buildAuctionsSuggestQuery(keyword);

    SearchHits<AuctionSearchDocument> hits =
        elasticsearchOperations.search(nativeQuery, AuctionSearchDocument.class);

    return hits.getSearchHits().stream()
        .map(
            hit ->
                auctionSearchDocumentMapper.toSuggestAuctionSearchResponseDto(
                    hit.getContent().getAuctionTitle()))
        .toList();
  }
}
