package org.example.searchservice.adapter.out.elasticsearch.querybuilder;


import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.stereotype.Component;

@Component
public class KeywordSearchQueryBuilder {
    public boolean isJasoInput(String input) {
        return input.matches(".*[ㄱ-ㅎㅏ-ㅣ]+.*");
    }
    public NativeQuery buildKeywordSearchQuery(String keyword) {

        if (isJasoInput(keyword)) {
            return NativeQuery.builder()
                    .withQuery(q -> q.match(m -> m.field("keyword.jaso").query(keyword)))
                    .withSort(Sort.by(Sort.Direction.DESC, "weight"))
                    .withMaxResults(5)
                    .build();
        } else {
            return NativeQuery.builder()
                    .withQuery(q -> q.match(m -> m.field("keyword").query(keyword)))
                    .withSort(Sort.by(Sort.Direction.DESC, "weight"))
                    .withMaxResults(5)
                    .build();
        }
    }
}
