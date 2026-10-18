package com.chalnakchalnak.search.application.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.chalnakchalnak.search.application.command.AuctionIndexCommand;
import com.chalnakchalnak.search.application.command.UpsertAuctionCommand;
import java.util.List;
import org.junit.jupiter.api.Test;

class AuctionBatchEventConverterTest {

  private final AuctionBatchEventConverter converter = new AuctionBatchEventConverter();

  @Test
  void convertsWithoutCallingCategoryOrTagServices() {
    AuctionIndexCommand source = new AuctionIndexCommand();
    source.setAuctionUuid("auction-1");
    source.setCategoryId(42);
    source.setTagIds(List.of(7L, 9L));

    UpsertAuctionCommand result = converter.toAuctionUpsertEventDto(source);

    assertThat(result.getAuctionUuid()).isEqualTo("auction-1");
    assertThat(result.getCategoryId()).isEqualTo(42);
    assertThat(result.getCategoryName()).isEqualTo("category-42");
    assertThat(result.getTagNames()).containsExactly("tag-7", "tag-9");
  }

  @Test
  void preservesNamesIncludedInTheEvent() {
    AuctionIndexCommand source = new AuctionIndexCommand();
    source.setCategoryId(42);
    source.setCategoryName("Books");
    source.setTagIds(List.of(7L));
    source.setTagNames(List.of("Novel"));

    UpsertAuctionCommand result = converter.toAuctionUpsertEventDto(source);

    assertThat(result.getCategoryName()).isEqualTo("Books");
    assertThat(result.getTagNames()).containsExactly("Novel");
  }
}
