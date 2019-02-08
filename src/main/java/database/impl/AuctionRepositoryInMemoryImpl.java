package database.impl;

import database.AuctionRepository;
import entities.Auction;

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
    public Auction getAuction(String auctionTitle) {
        return MemoryDataBase.getInstance().getAuction(auctionTitle);
    }
}
