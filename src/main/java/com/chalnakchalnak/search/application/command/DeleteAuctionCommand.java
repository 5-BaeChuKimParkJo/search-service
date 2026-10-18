package com.chalnakchalnak.search.application.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteAuctionCommand {

  private String auctionUuid;

  @Builder
  public DeleteAuctionCommand(String auctionUuid) {
    this.auctionUuid = auctionUuid;
  }
}
