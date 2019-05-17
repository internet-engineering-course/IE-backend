package ir.ac.ut.joboonja.repositories.impl;

import ir.ac.ut.joboonja.database.PreparedQuery;
import ir.ac.ut.joboonja.entities.Auction;
import ir.ac.ut.joboonja.entities.Bid;
import ir.ac.ut.joboonja.entities.Project;
import ir.ac.ut.joboonja.repositories.AuctionRepository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AuctionRepositoryImpl extends JDBCRepository<Auction> implements AuctionRepository {
    @Override
    public void insertBid(Bid bid) {
        String sql = String.format("INSERT INTO %s (projectId, userId, amount) VALUES (?, ?, ?);",
            getTableName());
        List<Object> params = Arrays.asList(bid.getProjectId(), bid.getUserId(), bid.getBidAmount());
        execUpdate(new PreparedQuery(sql, params));
    }

    @Override
    public Auction getAuction(String id) {
        String query = String.format("SELECT * FROM %s WHERE projectId = ?;", getTableName());
        return findOne(new PreparedQuery(query, Collections.singletonList(id)));
    }

    @Override
    public void insertAuction(Auction auction) {
        String query = "INSERT or IGNORE INTO Auction (projectId, userId) VALUES (?, ?);";
        List<Object> params = Arrays.asList(auction.getProjectId(), auction.getWinnerId());
        execUpdate(new PreparedQuery(query, params));
    }

    @Override
    public Auction getAuctionWinner(Project project) {
        String query = "select * from Auction where projectId = ?;";
        return findOne(new PreparedQuery(query, Collections.singletonList(project.getId())));
    }

    @Override
    String getTableName() {
        return "Bid";
    }

    @Override
    Auction toDomainModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rs = resultSet.getMetaData();
        if(rs.getColumnCount() == 3){
            Bid bid =  new Bid(
                    resultSet.getInt("userId"),
                    resultSet.getString("projectId"),
                    resultSet.getInt("amount")
            );
            return new Auction(bid.getProjectId(), Collections.singletonList(bid));
        }else{
            return new Auction(
                    resultSet.getString("projectId"),
                    resultSet.getInt("userId")
            );
        }
    }

//    @Override
//    List<Auction> merge(List<Auction> rawResult) {
//        Map<String, List<Auction>> auctions = rawResult.stream().collect(groupingBy(Auction::getProjectId));
//        LinkedList<Auction> result = new LinkedList<>();
//        for (String projectId: auctions.keySet()) {
//            LinkedList<Bid> offers = new LinkedList<>();
//            for (Auction a: auctions.get(projectId))
//                offers.add(a.getOffers().get(0));
//
//            Auction auction = auctions.get(projectId).get(0);
//            auction.setOffers(offers);
//            result.add(auction);
//        }
//        return result;
//    }

    public static String getCreateScript() {
        return "CREATE TABLE IF NOT EXISTS Bid\n"+
            "(\n"+
            "    projectId INTEGER,\n"+
            "    userId INTEGER,\n"+
            "    amount INTEGER,\n"+
            "    CONSTRAINT Bid_projectId_userId_pk PRIMARY KEY (projectId, userId),\n"+
            "    CONSTRAINT Bid_User_id_fk FOREIGN KEY (userId) REFERENCES User (id) ON DELETE CASCADE ON UPDATE CASCADE,\n"+
            "    CONSTRAINT Bid_Project_id_fk FOREIGN KEY (projectId) REFERENCES Project (id) ON DELETE CASCADE ON UPDATE CASCADE\n"+
            ");" +
            "create table if not exists Auction\n" +
            "(\n" +
            "\tprojectId varchar(36)\n" +
            "\t\tconstraint Auction_Project_id_fk\n" +
            "\t\t\treferences Project\n" +
            "\t\t\t\ton update cascade on delete cascade,\n" +
            "\tuserId int\n" +
            "\t\tconstraint Auction_User_id_fk\n" +
            "\t\t\treferences User,\n" +
            "\tconstraint Auction_pk\n" +
            "\t\tprimary key (projectId, userId)\n" +
            ");";
    }
}
