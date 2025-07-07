package org.example.searchservice.adapter.out.elasticsearch.querybuilder;


import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.stereotype.Component;

@Component
public class KeywordSearchQueryBuilder {

    private static final Sort SORT_BY_WEIGHT = Sort.by(Sort.Direction.DESC, "weight");

    private boolean isJasoInput(String input) {
        return input.matches(".*[ㄱ-ㅎㅏ-ㅣ]+.*");
    }

    public NativeQuery buildKeywordSearchQuery(String keyword) {

        return NativeQuery.builder()
                .withQuery(q -> q.bool(b -> b
                        // ① 정확·자소·엔그램·prefix 4가지를 모두 should로 넣고
                        .should(s -> s.match(m -> m.field("keyword").query(keyword)))
                        .should(s -> s.match(m -> m.field("keyword.jaso").query(keyword)))
                        .should(s -> s.matchPhrasePrefix(m -> m.field("keyword").query(keyword)))
                        .should(s -> s.match(m -> m.field("keyword.ngram").query(keyword)))
                ))
                // ② weight 정렬을 걸되, _score가 1차로 정렬되도록 secondary sort
                .withSort(Sort.by(Sort.Order.desc("_score"), SORT_BY_WEIGHT.getOrderFor("weight")))
                .withMaxResults(10) // 자동완성은 보통 10개
                .build();
    }
}
