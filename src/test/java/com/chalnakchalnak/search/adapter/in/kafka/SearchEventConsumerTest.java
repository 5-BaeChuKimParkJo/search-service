package com.chalnakchalnak.search.adapter.in.kafka;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.chalnakchalnak.search.adapter.in.kafka.controller.SearchEventConsumer;
import com.chalnakchalnak.search.adapter.in.kafka.mapper.AuctionMessageMapper;
import com.chalnakchalnak.search.adapter.in.kafka.mapper.ProductMessageMapper;
import com.chalnakchalnak.search.application.port.in.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.Acknowledgment;

class SearchEventConsumerTest {
  private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
  private final AuctionSearchUseCase auctions = mock(AuctionSearchUseCase.class);
  private final KeywordSearchUseCase keywords = mock(KeywordSearchUseCase.class);
  private final Acknowledgment acknowledgment = mock(Acknowledgment.class);
  private final SearchEventConsumer consumer =
      new SearchEventConsumer(
          auctions,
          keywords,
          mock(ProductSearchUseCase.class),
          mapper,
          new AuctionMessageMapper(mapper),
          new ProductMessageMapper(mapper));

  @Test
  void mapsTransportToCommandsBeforeAcknowledging() throws Exception {
    var payload =
        mapper.writeValueAsString(java.util.Map.of("auctionUuid", "one", "title", "test"));
    var message = mapper.writeValueAsString(java.util.Map.of("payload", payload, "op", "c"));
    consumer.consumeAuctionSearch(List.of("one"), List.of(message), acknowledgment);
    var order = inOrder(auctions, keywords, acknowledgment);
    order
        .verify(auctions)
        .saveAuctionBulk(argThat(commands -> commands.get(0).getAuctionUuid().equals("one")));
    order.verify(keywords).saveKeywordBulk(List.of("test"));
    order.verify(acknowledgment).acknowledge();
  }

  @Test
  void malformedPayloadIsNotAcknowledged() throws Exception {
    var message = mapper.writeValueAsString(java.util.Map.of("payload", "invalid-json", "op", "c"));
    assertThatThrownBy(
            () -> consumer.consumeAuctionSearch(List.of("one"), List.of(message), acknowledgment))
        .isInstanceOf(RuntimeException.class);
    verifyNoInteractions(acknowledgment, auctions, keywords);
  }
}
