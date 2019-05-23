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
    public static String getCreateAuctionScript(){
        return "create table if not exists Auction\n" +
                "(\n" +
                "\tprojectId varchar(36) not null,\n" +
                "\tuserId integer null,\n" +
                "\tconstraint Auction_pk\n" +
                "\t\tprimary key (projectId),\n" +
                "\tconstraint Auction_Project_id_fk\n" +
                "\t\tforeign key (projectId) references Project (id)\n" +
                "\t\t\ton update cascade on delete cascade,\n" +
                "\tconstraint Auction_User_id_fk\n" +
                "\t\tforeign key (userId) references User (id)\n" +
                "\t\t\ton update cascade on delete cascade\n" +
                ");\n" +
                "\n";
    }

    public static String getCreateScript() {
        return "create table if not exists Bid\n" +
                "(\n" +
                "\tprojectId varchar(36) not null,\n" +
                "\tuserId integer not null,\n" +
                "\tamount integer null,\n" +
                "\tconstraint Bid_pk\n" +
                "\t\tprimary key (projectId, userId),\n" +
                "\tconstraint Bid_Project_id_fk\n" +
                "\t\tforeign key (projectId) references Project (id)\n" +
                "\t\t\ton update cascade on delete cascade,\n" +
                "\tconstraint Bid_User_id_fk\n" +
                "\t\tforeign key (userId) references User (id)\n" +
                "\t\t\ton update cascade on delete cascade\n" +
                ");\n" +
                "\n";
    }
}
