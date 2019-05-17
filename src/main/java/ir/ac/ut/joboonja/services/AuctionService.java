package ir.ac.ut.joboonja.services;

import ir.ac.ut.joboonja.entities.*;
import ir.ac.ut.joboonja.exceptions.BadRequestException;
import ir.ac.ut.joboonja.models.BidAmount;
import ir.ac.ut.joboonja.repositories.AuctionRepository;
import ir.ac.ut.joboonja.repositories.ProjectRepository;
import ir.ac.ut.joboonja.repositories.UserRepository;
import ir.ac.ut.joboonja.repositories.impl.AuctionRepositoryImpl;
import ir.ac.ut.joboonja.repositories.impl.ProjectRepositoryImpl;
import ir.ac.ut.joboonja.repositories.impl.UserRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class AuctionService {

    private static AuctionRepository auctionRepository = new AuctionRepositoryImpl();
    private static ProjectRepository projectRepository = new ProjectRepositoryImpl();
    private static UserRepository userRepository = new UserRepositoryImpl();

    @Scheduled(fixedDelay = 1000*60)
    public static void holdAuction(){
        System.out.println("Auction...");
        List<Project> projects = projectRepository.getAllProjects();

        for(Project project:projects){
            if(project.getDeadline() < new Date().getTime()){
                Auction auction = auctionRepository.getAuction(project.getId());
                User winnerUser = null;
                if (auction == null) {
                    auctionRepository.insertAuction(new Auction(project.getId() , 0));
                    continue;
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
                auctionRepository.insertAuction(new Auction(project.getId() , winnerUser.getId()));
            }
        }
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

    public static Bid bidProject(Project project, Integer bidAmount, User user) {
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

    public static User getAuctionWinner(Project project){
        Auction auction = auctionRepository.getAuctionWinner(project);
        if(auction == null || auction.getWinnerId() == 0){
            return new User();
        }
        return userRepository.getUserById(auction.getWinnerId());
    }
}
