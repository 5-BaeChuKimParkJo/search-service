package com.chalnakchalnak.search.application.service;

import com.chalnakchalnak.search.application.command.ProductIndexCommand;
import com.chalnakchalnak.search.application.converter.ProductBatchEventConverter;
import com.chalnakchalnak.search.application.port.in.ProductSearchUseCase;
import com.chalnakchalnak.search.application.port.out.ProductSearchRepositoryPort;
import com.chalnakchalnak.search.application.port.out.TaxonomyLookupPort;
import com.chalnakchalnak.search.application.query.ProductSearchQuery;
import com.chalnakchalnak.search.application.result.ProductSearchResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSearchService implements ProductSearchUseCase {
  private final ProductSearchRepositoryPort products;
  private final ProductBatchEventConverter converter;
  private final TaxonomyLookupPort taxonomy;

  @Override
  public List<ProductSearchResult> searchProduct(ProductSearchQuery query) {
    return products.search(query);
  }

  @Override
  public void saveProductBulk(List<ProductIndexCommand> commands) {
    for (var command : commands) {
      var category = taxonomy.getCategory(command.getCategoryId());
      var tags =
          command.getTagIdList().stream()
              .map(
                  id -> {
                    try {
                      return taxonomy.getTagById(id);
                    } catch (Exception error) {
                      throw new RuntimeException("Failed to fetch tag with ID: " + id, error);
                    }
                  })
              .toList();
      products.save(converter.toProductUpsertDto(command, category, tags));
    }
  }
}
