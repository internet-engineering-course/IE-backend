package ir.ac.ut.joboonja.database.impl;

import ir.ac.ut.joboonja.database.AuctionRepository;
import ir.ac.ut.joboonja.entities.Auction;

public class AuctionRepositoryInMemoryImpl implements AuctionRepository {
    @Override
    public boolean auctionExists(Auction auction) {
        return MemoryDataBase.getInstance().auctionExists(auction);
    }

    @Override
    public void insertAuction(Auction auction) {
        MemoryDataBase.getInstance().insertAuction(auction);
    }

    @Override
    public Auction getAuction(String id) {
        return MemoryDataBase.getInstance().getAuction(id);
    }
}
