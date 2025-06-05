package org.example.searchservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "product")
public class Product {
    @Id
    private String id;

    private String name;
    private String description;
}
