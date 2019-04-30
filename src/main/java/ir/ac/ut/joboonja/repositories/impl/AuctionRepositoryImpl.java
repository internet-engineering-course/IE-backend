package ir.ac.ut.joboonja.repositories.impl;

import ir.ac.ut.joboonja.entities.Auction;
import ir.ac.ut.joboonja.entities.Bid;
import ir.ac.ut.joboonja.repositories.AuctionRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class AuctionRepositoryImpl extends JDBCRepository<Auction> implements AuctionRepository {
    @Override
    public void insertBid(Bid bid) {
        String sql = String.format("INSERT INTO %s (projectId, userId, amount) VALUES ('%s', %d, %d);",
            getTableName(), bid.getProjectId(), bid.getUserId(), bid.getBidAmount());
        execUpdate(sql);
    }

    @Override
    public Auction getAuction(String id) {
        String query = String.format("SELECT * FROM %s WHERE projectId = '%s';", getTableName(), id);
        return findOne(query);
    }

    @Override
    String getTableName() {
        return "Bid";
    }

    @Override
    Auction toDomainModel(ResultSet resultSet) throws SQLException {
        Bid bid =  new Bid(
            resultSet.getInt("userId"),
            resultSet.getString("projectId"),
            resultSet.getInt("amount")
        );
        return new Auction(bid.getProjectId(), Collections.singletonList(bid));
    }

    @Override
    List<Auction> merge(List<Auction> rawResult) {
        Map<String, List<Auction>> auctions = rawResult.stream().collect(groupingBy(Auction::getProjectId));
        LinkedList<Auction> result = new LinkedList<>();
        for (String projectId: auctions.keySet()) {
            LinkedList<Bid> offers = new LinkedList<>();
            for (Auction a: auctions.get(projectId))
                offers.add(a.getOffers().get(0));

            Auction auction = auctions.get(projectId).get(0);
            auction.setOffers(offers);
            result.add(auction);
        }
        return result;
    }

    public static String getCreateScript() {
        return "CREATE TABLE IF NOT EXISTS Bid\n"+
            "(\n"+
            "    projectId INTEGER,\n"+
            "    userId INTEGER,\n"+
            "    amount INTEGER,\n"+
            "    CONSTRAINT Bid_projectId_userId_pk PRIMARY KEY (projectId, userId),\n"+
            "    CONSTRAINT Bid_User_id_fk FOREIGN KEY (userId) REFERENCES User (id) ON DELETE CASCADE ON UPDATE CASCADE,\n"+
            "    CONSTRAINT Bid_Project_id_fk FOREIGN KEY (projectId) REFERENCES Project (id) ON DELETE CASCADE ON UPDATE CASCADE\n"+
            ");";
    }
}
