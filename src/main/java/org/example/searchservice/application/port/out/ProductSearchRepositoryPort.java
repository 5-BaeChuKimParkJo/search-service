package org.example.searchservice.application.port.out;

import org.example.searchservice.application.dto.out.ProductUpsertEventDto;

public interface ProductSearchRepositoryPort {

    void save(ProductUpsertEventDto productUpsertEventDto);
}
