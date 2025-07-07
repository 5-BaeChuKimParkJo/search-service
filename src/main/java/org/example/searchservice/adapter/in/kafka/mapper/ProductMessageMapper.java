package org.example.searchservice.adapter.in.kafka.mapper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.searchservice.adapter.in.kafka.event.product.ProductBatchEvent;
import org.example.searchservice.adapter.in.kafka.event.product.ProductEvent;
import org.example.searchservice.application.dto.out.ProductBatchEventDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMessageMapper {

    private ObjectMapper objectMapper;

    public ProductBatchEventDto toProductBatchEventDto(String payload, String op) throws JsonProcessingException {

        ProductBatchEventDto productBatchEventDto = objectMapper.readValue(payload, ProductBatchEventDto.class);
        productBatchEventDto.setOp(op);

        return productBatchEventDto;

    }
}
