package org.example.searchservice.application.port.in;

import org.example.searchservice.adapter.in.kafka.event.product.ProductEvent;

import java.util.List;

public interface ProductSearchUseCase {

    void saveProductBulk(List<ProductEvent> productEvents);
}
