package org.example.searchservice.adapter.in.kafka.event.product;

public class ProductEvent {

    private String id;
    private String aggregateType;
    private String aggregateId;
    private String eventType;
    private String payload; // JSON String
    private String op;
    private String createdAt;
    private String __deleted;
}
