package org.example.searchservice.adapter.out.elasticsearch.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ElasticSearchConfigTest {

    @Test
    void convertsHttpUrlToSpringDataConnectionAddress() {
        assertEquals(
                "100.84.9.65:19200",
                ElasticSearchConfig.connectionAddress("http://100.84.9.65:19200")
        );
    }
}
