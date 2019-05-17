package ir.ac.ut.joboonja.repositories;

import ir.ac.ut.joboonja.entities.Auction;
import ir.ac.ut.joboonja.entities.Bid;
import ir.ac.ut.joboonja.entities.Project;

public interface AuctionRepository {
    void insertBid(Bid bid);
    Auction getAuction(String id);
    void insertAuction(Auction auction);
    Auction getAuctionWinner(Project project);
}
