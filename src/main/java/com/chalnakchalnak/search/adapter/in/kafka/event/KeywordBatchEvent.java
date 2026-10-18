package com.chalnakchalnak.search.adapter.in.kafka.event;

import com.chalnakchalnak.search.domain.AuctionImage;
import java.util.List;
import lombok.Getter;

@Getter
public class KeywordBatchEvent {

  private String endAt;
  private String title;
  private List<AuctionImage> images;
  private List<Long> tagIds;
  private String startAt;
  private int version;
  private String createdAt;
  private int viewCount;
  private int categoryId;
  private int currentBid;
  private int minimumBid;
  private String sellerUuid;
  private String auctionUuid;
  private String description;
  private boolean isDirectDeal;
  private String thumbnailKey;
  private String thumbnailUrl;
  private String productCondition;
  private String directDealLocation;
}
