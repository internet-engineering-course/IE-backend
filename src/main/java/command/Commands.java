package command;

import database.AuctionRepository;
import database.ProjectRepository;
import database.UserRepository;
import database.impl.AuctionRepositoryInMemoryImpl;
import database.impl.ProjectRepositoryInMemoryImpl;
import database.impl.UserRepositoryInMemoryImpl;
import entities.Auction;
import entities.Project;
import entities.Skill;
import entities.User;
import exceptions.DeserializeException;
import models.BidInfo;
import models.ProjectTitle;
import utilities.Deserializer;

import java.util.LinkedList;
import java.util.List;

public class Commands {

    private static AuctionRepository auctionRepository = new AuctionRepositoryInMemoryImpl();
    private static UserRepository userRepository = new UserRepositoryInMemoryImpl();
    private static ProjectRepository projectRepository = new ProjectRepositoryInMemoryImpl();


    public static List<Project> getValidProjects(User user){
        List<Project>  projects= projectRepository.getAllProjects();
        LinkedList<Project> result = new LinkedList<>();
        for(Project project:projects){
            if(hasEnoughSkills(user , project)) {
                result.add(project);
            }
        }
        return result;
    }

    public static User getDefaultUser(){
        return userRepository.getUser("test");
    }

    public static User getUserById(Integer id){
        return userRepository.getUserById(id);
    }

    public static Project getProjectById(String id){
        return projectRepository.getProjectById(id);
    }

    public static boolean hasEnoughSkills(User user , Project project) {

        if (user == null || project == null)
            return false;

        boolean meets = true;
        for (Skill skill: project.getSkills()) {
            int skillIndex = user.getSkills().indexOf(skill);
            if (skillIndex == -1)
                meets = false;
            else if (user.getSkills().get(skillIndex).getPoint() < skill.getPoint())
                meets = false;
        }

        return meets;
    }

    static void register(String json) throws DeserializeException {
        User user = Deserializer.deserialize(json , User.class);
        userRepository.insertUser(user);
    }

    static void addProject(String json) throws DeserializeException {
        Project project = Deserializer.deserialize(json , Project.class);
        projectRepository.insertProject(project);
    }

    static boolean addBid(String json) throws DeserializeException {
        BidInfo bidInfo = Deserializer.deserialize(json , BidInfo.class);
        if (!meetsRequirements(bidInfo))
            return false;

        Auction auction = auctionRepository.getAuction(bidInfo.getProjectTitle());
        if (auction == null) {
            auction = new Auction(bidInfo.getProjectTitle());
            auctionRepository.insertAuction(auction);
        }
        auction.addOffer(bidInfo);
        return true;
    }

    private static boolean meetsRequirements(BidInfo bidInfo) {
        User user = userRepository.getUser(bidInfo.getBiddingUser());
        Project project = projectRepository.getProject(bidInfo.getProjectTitle());
        if (user == null || project == null)
            return false;

        if(bidInfo.getBidAmount() > project.getBudget())
            return false;

        return hasEnoughSkills(user , project);
    }


    static User auction(String json) throws DeserializeException {
        ProjectTitle projectTitle = Deserializer.deserialize(json , ProjectTitle.class);
        Auction auction = auctionRepository.getAuction(projectTitle.getProjectTitle());
        Project project = projectRepository.getProject(auction.getProjectTitle());
        User winnerUser = null;
        double maxPoint = 0;
        for(BidInfo bidInfo: auction.getOffers()){
            User user = userRepository.getUser(bidInfo.getBiddingUser());
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
}
