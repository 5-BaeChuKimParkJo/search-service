package org.example.searchservice.application.converter;

import org.example.searchservice.application.dto.in.AuctionBatchEventDto;
import org.example.searchservice.application.dto.in.AuctionUpsertEventDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AuctionBatchEventConverterTest {

    private final AuctionBatchEventConverter converter = new AuctionBatchEventConverter();

    @Test
    void convertsWithoutCallingCategoryOrTagServices() {
        AuctionBatchEventDto source = new AuctionBatchEventDto();
        source.setAuctionUuid("auction-1");
        source.setCategoryId(42);
        source.setTagIds(List.of(7L, 9L));

        AuctionUpsertEventDto result = converter.toAuctionUpsertEventDto(source);

        assertThat(result.getAuctionUuid()).isEqualTo("auction-1");
        assertThat(result.getCategoryId()).isEqualTo(42);
        assertThat(result.getCategoryName()).isEqualTo("category-42");
        assertThat(result.getTagNames()).containsExactly("tag-7", "tag-9");
    }

    @Test
    void preservesNamesIncludedInTheEvent() {
        AuctionBatchEventDto source = new AuctionBatchEventDto();
        source.setCategoryId(42);
        source.setCategoryName("Books");
        source.setTagIds(List.of(7L));
        source.setTagNames(List.of("Novel"));

        AuctionUpsertEventDto result = converter.toAuctionUpsertEventDto(source);

        assertThat(result.getCategoryName()).isEqualTo("Books");
        assertThat(result.getTagNames()).containsExactly("Novel");
    }
}
