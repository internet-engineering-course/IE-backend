package database;

import entities.Auction;

public interface AuctionRepository {
    boolean auctionExists(Auction auction);
    void insertAuction(Auction auction);
    Auction getAuction(String id);
}
