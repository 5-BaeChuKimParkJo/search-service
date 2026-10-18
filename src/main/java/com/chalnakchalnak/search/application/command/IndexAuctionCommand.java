package com.chalnakchalnak.search.application.command;

import com.chalnakchalnak.search.domain.AuctionImage;
import java.time.Instant;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndexAuctionCommand {

  private Instant endAt;
  private String title;
  private List<AuctionImage> images;
  private List<Long> tagIds;
  private Instant startAt;
  private int version;
  private Instant createdAt;
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
  private String categoryName;
  private List<String> tagNames;
}
