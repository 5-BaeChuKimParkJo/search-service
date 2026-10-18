package com.chalnakchalnak.search.application.port.out;

import com.chalnakchalnak.search.application.command.UpsertProductCommand;
import com.chalnakchalnak.search.application.query.ProductSearchQuery;
import com.chalnakchalnak.search.application.result.ProductSearchResult;
import java.util.List;

public interface ProductSearchRepositoryPort {

  List<ProductSearchResult> search(ProductSearchQuery query);

  void save(UpsertProductCommand productUpsertEventDto);
}
