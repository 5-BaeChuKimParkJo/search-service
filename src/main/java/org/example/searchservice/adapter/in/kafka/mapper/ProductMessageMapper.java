package org.example.searchservice.adapter.in.kafka.mapper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.searchservice.adapter.in.kafka.event.product.ProductBatchEvent;
import org.example.searchservice.adapter.in.kafka.event.product.ProductEvent;
import org.example.searchservice.application.dto.out.ProductBatchEventDto;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class ProductMessageMapper {

    private final ObjectMapper objectMapper;

    public ProductBatchEventDto toProductBatchEventDto(String payload, String op) throws JsonProcessingException {

        try {

            log.info("Converting payload to ProductBatchEventDto: {}", payload);
            ProductBatchEventDto productBatchEventDto = objectMapper.readValue(payload, ProductBatchEventDto.class);
            productBatchEventDto.setOp(op);

            return productBatchEventDto;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
