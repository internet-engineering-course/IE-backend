package ir.ac.ut.joboonja.repositories;

import ir.ac.ut.joboonja.entities.Auction;
import ir.ac.ut.joboonja.entities.Bid;

public interface AuctionRepository {
    void insertBid(Bid bid);
    Auction getAuction(String id);
}
