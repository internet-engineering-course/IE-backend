package ir.ac.ut.joboonja.repositories.impl.memory;

import ir.ac.ut.joboonja.entities.Bid;
import ir.ac.ut.joboonja.entities.Project;
import ir.ac.ut.joboonja.repositories.AuctionRepository;
import ir.ac.ut.joboonja.entities.Auction;

public class AuctionRepositoryInMemoryImpl implements AuctionRepository {
    @Override
    public void insertBid(Bid bid) {
        Auction auction = getAuction(bid.getProjectId());
        if(auction == null)
            auction = new Auction(bid.getProjectId());
        auction.addOffer(bid);
        MemoryDataBase.getInstance().insertAuction(auction);
    }

    @Override
    public Auction getAuction(String id) {
        return MemoryDataBase.getInstance().getAuction(id);
    }

    @Override
    public void insertAuction(Auction auction) {

    }

    @Override
    public Auction getAuctionWinner(Project project) {
        return null;
    }
}
