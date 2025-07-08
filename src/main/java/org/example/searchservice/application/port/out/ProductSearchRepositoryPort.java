package org.example.searchservice.application.port.out;

import org.example.searchservice.application.dto.out.GetProductSearchResponseDto;
import org.example.searchservice.application.dto.out.ProductUpsertEventDto;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;

import java.util.List;

public interface ProductSearchRepositoryPort {

    List<GetProductSearchResponseDto> search(NativeQuery nativeQuery);
    void save(ProductUpsertEventDto productUpsertEventDto);
}
