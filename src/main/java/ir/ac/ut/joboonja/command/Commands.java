package ir.ac.ut.joboonja.command;

import ir.ac.ut.joboonja.database.*;
import ir.ac.ut.joboonja.database.impl.*;
import ir.ac.ut.joboonja.entities.*;
import ir.ac.ut.joboonja.models.BidInfo;
import ir.ac.ut.joboonja.models.EndorsableSkill;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Commands {

    private static AuctionRepository auctionRepository = new AuctionRepositoryInMemoryImpl();
    private static UserRepository userRepository = new UserRepositoryInMemoryImpl();
    private static ProjectRepository projectRepository = new ProjectRepositoryInMemoryImpl();
    private static SkillRepository skillRepository = new SkillRepositoryInMemoryImpl();
    private static EndorseRepository endorseRepository = new EndorseRepositoryInMemoryImpl();

    public static List<User> getAllUsers(User user){
        List<User> users = userRepository.getAllUser();
        List<User> newList = new ArrayList<User>(users);
        for(User u:newList){
            if(u.getId().equals(user.getId())){
                newList.remove(u);
                break;
            }
        }
        return newList;
    }
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
        return userRepository.getUser("ali");
    }

    public static User getUserById(Integer id){
        return userRepository.getUserById(id);
    }

    public static Project getProjectById(String id){
        return projectRepository.getProjectById(id);
    }

    public static List<Skill> getAllSkills() {
        return skillRepository.getAllSkills();
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

//    static void register(String json) throws SerializeException {
//        User user = Deserializer.deserialize(json , User.class);
//        userRepository.insertUser(user);
//    }
//
//    static void addProject(String json) throws SerializeException {
//        Project project = Deserializer.deserialize(json , Project.class);
//        projectRepository.insertProject(project);
//    }

    public static BidInfo addBid(Project project , User user , Integer bidAmount){
        Auction auction = auctionRepository.getAuction(project.getId());
        if(auction == null) {
            auction = new Auction(project.getId());
            auctionRepository.insertAuction(auction);
        }
        BidInfo bidInfo = new BidInfo(user.getId(), project.getId(), bidAmount);
        auction.addOffer(bidInfo);
        return bidInfo;
    }

    public static boolean userIsBidBefore(Project project, User user){
        Auction auction = auctionRepository.getAuction(project.getId());
        if(auction == null) {
            return false;
        }
        else {
            for(BidInfo bid:auction.getOffers()) {
                if(bid.getUserId().equals(user.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

//    private static boolean meetsRequirements(BidInfo bidInfo) {
//        User user = userRepository.getUser(bidInfo.getUserId());
//        Project project = projectRepository.getProject(bidInfo.getProjectTitle());
//        if (user == null || project == null)
//            return false;
//
//        if(bidInfo.getBidAmount() > project.getBudget())
//            return false;
//
//        return hasEnoughSkills(user , project);
//    }

//
//    static User auction(String json) throws DeserializeException {
//        ProjectTitle projectTitle = Deserializer.deserialize(json , ProjectTitle.class);
//        Auction auction = auctionRepository.getAuction(projectTitle.getProjectTitle());
//        Project project = projectRepository.getProject(auction.getProjectTitle());
//        User winnerUser = null;
//        double maxPoint = 0;
//        for(BidInfo bidInfo: auction.getOffers()){
//            User user = userRepository.getUser(bidInfo.getUserId());
//            double point =  calAuctionPoint(project , user);
//            point += project.getBudget() - bidInfo.getBidAmount();
//            if(maxPoint < point) {
//                maxPoint = point;
//                winnerUser = user;
//            }
//        }
//        return winnerUser;
//    }

    private static double calAuctionPoint(Project project , User user){
        double sum = 0;

        for (Skill skill: project.getSkills()) {
            int skillIndex = user.getSkills().indexOf(skill);
            int userPoint = user.getSkills().get(skillIndex).getPoint();
            sum = 10000 * Math.pow((double) (userPoint - skill.getPoint()) , 2);
        }

        return sum;
    }

    public static void updateUserSkill(Integer userId, String skillName) {
        userRepository.updateUserSkill(userId, skillName);
    }

    public static void deleteUserSkill(Integer userId, String skillName) {
        userRepository.deleteUserSkill(userId, skillName);
    }

    public static Endorse endorseSkill(Integer endorserId, Integer endorsedId, String skillName) {
        Endorse endorse = new Endorse(endorserId, endorsedId, skillName);
        List<Endorse> endorses = endorseRepository.getEndorses(endorserId);
        for (Endorse e: endorses)
            if (e.getEndorsedId().equals(endorsedId) && e.getSkillName().equals(skillName)) {
                System.out.println("Already endorsed!");
                return null;
            }
        endorseRepository.insertEndorse(endorse);
        userRepository.updateUserSkillPoint(endorsedId, skillName, 1);
        return endorse;
    }

    public static List<EndorsableSkill> getUserEndorsableSkills(Integer endorserId, Integer endorsedId) {
        User endorsed = userRepository.getUserById(endorsedId);
        List<Endorse> endorses = endorseRepository.getEndorses(endorserId);
        List<EndorsableSkill> result = new LinkedList<EndorsableSkill>();
        for (Skill skill: endorsed.getSkills()) {
            boolean endorsable = true;
            for (Endorse endorse : endorses)
                if (endorse.getEndorsedId().equals(endorsedId) && skill.getName().equals(endorse.getSkillName()))
                    endorsable = false;
            result.add(new EndorsableSkill(skill, endorsable));
        }
        return result;
    }

    public static Integer getUserBidAmount(Project project, User user) {
        Auction auction = auctionRepository.getAuction(project.getId());
        for(BidInfo bid:auction.getOffers()) {
            if(bid.getUserId().equals(user.getId())) {
                return bid.getBidAmount();
            }
        }
        return 0;
    }
}
