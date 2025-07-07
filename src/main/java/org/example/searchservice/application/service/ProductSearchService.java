package org.example.searchservice.application.service;

import org.example.searchservice.adapter.in.kafka.event.product.ProductEvent;
import org.example.searchservice.application.port.in.ProductSearchUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSearchService implements ProductSearchUseCase {


    @Override
    public void saveProductBulk(List<ProductEvent> productEvents) {

    }
}
