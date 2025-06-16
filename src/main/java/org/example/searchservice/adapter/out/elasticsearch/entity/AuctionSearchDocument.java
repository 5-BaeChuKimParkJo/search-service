package org.example.searchservice.adapter.out.elasticsearch.entity;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "auction_search")
public class AuctionSearchDocument {

    @Id
    private String id;
    private String auctionUuid;
    private String title;
    private String description;
    private String status;
    private String thumbnailKey;
    private String directDealLocation;

    @Builder
    public AuctionSearchDocument(String id, String auctionUuid, String title, String description,
                                 String status, String thumbnailKey, String directDealLocation) {
        this.id = id;
        this.auctionUuid = auctionUuid;
        this.title = title;
        this.description = description;
        this.status = status;
        this.thumbnailKey = thumbnailKey;
        this.directDealLocation = directDealLocation;
    }

}
