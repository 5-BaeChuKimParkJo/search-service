package org.example.searchservice.adapter.in.kafka.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.searchservice.adapter.in.kafka.event.AuctionEvent;
import org.example.searchservice.adapter.in.kafka.event.KeywordBatchEvent;
import org.example.searchservice.application.dto.in.AuctionBatchEventDto;
import org.example.searchservice.application.dto.in.KeywordBatchEventDto;
import org.example.searchservice.common.exception.BaseException;
import org.example.searchservice.common.response.BaseResponseStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeywordMessageMapper {

    private final ObjectMapper objectMapper;

    public KeywordBatchEvent toKeywordBatchEvent(AuctionEvent auctionEvent) {

        try {
            return objectMapper.readValue(
                    auctionEvent.getPayload(),
                    KeywordBatchEvent.class
            );
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_PARSE_KEYWORD_BATCH_EVENT);
        }
    }

    public KeywordBatchEventDto toKeywordBatchEventDto(String keyword, int weight) {
        return KeywordBatchEventDto.builder()
                .keyword(keyword)
                .weight(weight)
                .build();

    }



}
