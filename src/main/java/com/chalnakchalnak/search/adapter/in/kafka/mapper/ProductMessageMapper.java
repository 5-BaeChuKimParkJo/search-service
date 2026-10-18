package com.chalnakchalnak.search.adapter.in.kafka.mapper;

import com.chalnakchalnak.search.application.command.ProductIndexCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductMessageMapper {

  private final ObjectMapper objectMapper;

  public ProductIndexCommand toProductBatchEventDto(String payload, String op)
      throws JsonProcessingException {

    try {

      log.info("Converting payload to ProductIndexCommand: {}", payload);
      ProductIndexCommand productBatchEventDto =
          objectMapper.readValue(payload, ProductIndexCommand.class);
      productBatchEventDto.setOp(op);

      return productBatchEventDto;
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
