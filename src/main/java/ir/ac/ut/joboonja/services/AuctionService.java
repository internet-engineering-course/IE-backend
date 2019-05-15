package ir.ac.ut.joboonja.services;

import ir.ac.ut.joboonja.entities.*;
import ir.ac.ut.joboonja.exceptions.BadRequestException;
import ir.ac.ut.joboonja.models.BidAmount;
import ir.ac.ut.joboonja.repositories.AuctionRepository;
import ir.ac.ut.joboonja.repositories.impl.AuctionRepositoryImpl;

import java.util.Date;

import static ir.ac.ut.joboonja.services.UserService.getDefaultUser;

public class AuctionService {

    private static AuctionRepository auctionRepository = new AuctionRepositoryImpl();

    public static User holdAuction(Project project){
        Auction auction = auctionRepository.getAuction(project.getId());
        User winnerUser = null;
        if (auction == null) {
            return winnerUser;
        }
        double maxPoint = 0;
        for(Bid bidInfo: auction.getOffers()){
            User user = UserService.getUserById(bidInfo.getUserId());
            double point =  calAuctionPoint(project , user);
            point += project.getBudget() - bidInfo.getBidAmount();
            if(maxPoint < point) {
                maxPoint = point;
                winnerUser = user;
            }
        }

        return winnerUser;
    }

    private static double calAuctionPoint(Project project , User user){
        double sum = 0;

        for (Skill skill: project.getSkills()) {
            int skillIndex = user.getSkills().indexOf(skill);
            int userPoint = user.getSkills().get(skillIndex).getPoint();
            sum = 10000 * Math.pow((double) (userPoint - skill.getPoint()) , 2);
        }

        return sum;
    }

    public static Bid bidProject(Project project, Integer bidAmount) {
        User user = getDefaultUser();
        if (hasUserBid(project, user).getBidAmount() != -1)
            throw new BadRequestException("User has already bidded!");
        if (bidAmount > project.getBudget())
            throw new BadRequestException("Bid amount is higher than project budget!");
        if (bidAmount < 0)
            throw new BadRequestException("Bid amount should be positive!");
        if (project.getDeadline() < (new Date()).getTime())
            throw new BadRequestException("Project deadline has been passed!");

        Bid bid = new Bid(user.getId(), project.getId(), bidAmount);
        auctionRepository.insertBid(bid);
        return bid;
    }

    public static BidAmount hasUserBid(Project project, User user){
        Auction auction = auctionRepository.getAuction(project.getId());
        BidAmount bidAmount = new BidAmount();
        if(auction == null) {
            bidAmount.setBidAmount(-1);
            return bidAmount;
        }
        else {
            for(Bid bid:auction.getOffers()) {
                if(bid.getUserId().equals(user.getId())) {
                    bidAmount.setBidAmount(bid.getBidAmount());
                    return bidAmount;
                }
            }
        }
        bidAmount.setBidAmount(-1);
        return bidAmount;
    }
}
