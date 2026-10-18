package com.chalnakchalnak.search.application.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateAuctionIndexCommand {

  private String auctionUuid;
  private String auctionTitle;
  private String description;
  private String status;
  private String thumbnailKey;
  private String directDealLocation;

  @Builder
  public CreateAuctionIndexCommand(
      String auctionUuid,
      String auctionTitle,
      String description,
      String status,
      String thumbnailKey,
      String directDealLocation) {
    this.auctionUuid = auctionUuid;
    this.auctionTitle = auctionTitle;
    this.description = description;
    this.status = status;
    this.thumbnailKey = thumbnailKey;
    this.directDealLocation = directDealLocation;
  }
}
