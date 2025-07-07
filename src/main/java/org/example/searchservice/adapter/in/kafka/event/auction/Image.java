package org.example.searchservice.adapter.in.kafka.event.auction;

import lombok.Data;

@Data
public class Image {
    private String key;
    private String url;
    private int order;
    private int auctionImageId;
}
