package com.chalnakchalnak.search.adapter.out.elasticsearch;

import static org.assertj.core.api.Assertions.assertThat;

import com.chalnakchalnak.search.adapter.out.elasticsearch.entity.*;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;

class DocumentIdentityTest {
  @Test
  void readsTheExistingClassIdentityAfterPackageRename() throws Exception {
    var context = new SimpleElasticsearchMappingContext();
    context.setInitialEntitySet(
        Set.of(
            AuctionSearchDocument.class, ProductSearchDocument.class, KeywordSearchDocument.class));
    context.afterPropertiesSet();
    var converter = new MappingElasticsearchConverter(context);
    converter.afterPropertiesSet();
    var document =
        Document.from(
            Map.of(
                "_class",
                "org.example.searchservice.adapter.out.elasticsearch.entity.AuctionSearchDocument",
                "auctionUuid",
                "one",
                "title",
                "Existing auction"));
    assertThat(converter.read(AuctionSearchDocument.class, document).getAuctionUuid())
        .isEqualTo("one");
    var output = Document.create();
    converter.write(converter.read(AuctionSearchDocument.class, document), output);
    assertThat(output.get("_class")).isEqualTo(document.get("_class"));
  }
}
