package com.chalnakchalnak.search.application.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.chalnakchalnak.search.application.command.AuctionIndexCommand;
import com.chalnakchalnak.search.application.command.UpsertAuctionCommand;
import com.chalnakchalnak.search.application.converter.AuctionBatchEventConverter;
import com.chalnakchalnak.search.application.port.out.*;
import java.util.List;
import org.junit.jupiter.api.Test;

class AuctionIndexServiceTest {
  private final AuctionSearchRepositoryPort repository = mock(AuctionSearchRepositoryPort.class);
  private final TaxonomyLookupPort taxonomy = mock(TaxonomyLookupPort.class);
  private final AuctionBatchEventConverter converter = mock(AuctionBatchEventConverter.class);
  private final AuctionSearchService service =
      new AuctionSearchService(
          repository, mock(KeywordSearchRepositoryPort.class), converter, taxonomy);

  private AuctionIndexCommand command(String uuid, String op) {
    var command = new AuctionIndexCommand();
    command.setAuctionUuid(uuid);
    command.setOp(op);
    return command;
  }

  @Test
  void lastDeleteWinsWithoutFetchingTaxonomy() {
    service.saveAuctionBulk(List.of(command("one", "c"), command("one", "d")));
    verify(repository).upsertAuctionBulk(List.of());
    verify(repository)
        .deleteAuctionBulk(
            argThat(
                deletes -> deletes.size() == 1 && deletes.get(0).getAuctionUuid().equals("one")));
    verifyNoInteractions(taxonomy, converter);
  }

  @Test
  void lastUpdateReplacesEarlierDelete() {
    var update = command("one", "u");
    var indexed = UpsertAuctionCommand.builder().auctionUuid("one").build();
    when(converter.toAuctionUpsertEventDto(update)).thenReturn(indexed);
    service.saveAuctionBulk(List.of(command("one", "d"), update));
    verify(repository).upsertAuctionBulk(List.of(indexed));
    verify(repository).deleteAuctionBulk(List.of());
  }

  @Test
  void legacySingleIndexPreservesCategoryFailureContract() {
    when(taxonomy.getCategory(anyInt())).thenThrow(new RuntimeException("offline"));
    var command = mock(com.chalnakchalnak.search.application.command.IndexAuctionCommand.class);
    assertThatThrownBy(() -> service.saveAuction(command))
        .isInstanceOf(com.chalnakchalnak.search.common.exception.BaseException.class);
    verifyNoInteractions(repository);
  }
}
