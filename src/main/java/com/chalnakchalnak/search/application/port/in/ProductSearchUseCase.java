package com.chalnakchalnak.search.application.port.in;

import com.chalnakchalnak.search.application.command.ProductIndexCommand;
import com.chalnakchalnak.search.application.query.ProductSearchQuery;
import com.chalnakchalnak.search.application.result.ProductSearchResult;
import java.util.List;

public interface ProductSearchUseCase {

  List<ProductSearchResult> searchProduct(ProductSearchQuery getProductSearchRequestDto);

  void saveProductBulk(List<ProductIndexCommand> commands);
}
