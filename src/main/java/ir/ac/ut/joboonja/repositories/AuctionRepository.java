package ir.ac.ut.joboonja.repositories;

import ir.ac.ut.joboonja.entities.Auction;

public interface AuctionRepository {
    boolean auctionExists(Auction auction);
    void insertAuction(Auction auction);
    Auction getAuction(String id);
}
