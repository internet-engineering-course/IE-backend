package ir.ac.ut.joboonja.command;

import ir.ac.ut.joboonja.database.*;
import ir.ac.ut.joboonja.database.impl.*;
import ir.ac.ut.joboonja.entities.*;
import ir.ac.ut.joboonja.exceptions.BadRequestException;
import ir.ac.ut.joboonja.exceptions.ForbiddenException;
import ir.ac.ut.joboonja.exceptions.NotFoundException;
import ir.ac.ut.joboonja.models.BidInfo;
import ir.ac.ut.joboonja.models.EndorsableSkill;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Commands {

    private static AuctionRepository auctionRepository = new AuctionRepositoryInMemoryImpl();
    private static UserRepository userRepository = new UserRepositoryInMemoryImpl();
    private static ProjectRepository projectRepository = new ProjectRepositoryInMemoryImpl();
    private static SkillRepository skillRepository = new SkillRepositoryInMemoryImpl();
    private static EndorseRepository endorseRepository = new EndorseRepositoryInMemoryImpl();

    public static List<User> getAllUsers() {
        User user = Commands.getDefaultUser();
        List<User> users = userRepository.getAllUser();
        List<User> newList = new ArrayList<>(users);
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
        User user = userRepository.getUserById(id);
        if (user == null)
            throw new NotFoundException("User not found");
        return user;
    }

    public static Project getProjectById(String id) {
        User user = getDefaultUser();
        Project project = projectRepository.getProjectById(id);
        if (project == null)
            throw new NotFoundException("Project not found!");
        if (!hasEnoughSkills(user, project))
            throw new ForbiddenException("Access to project is forbidden!");
        return project;
    }

    public static List<Skill> getAllSkills() {
        return skillRepository.getAllSkills();
    }

    private static boolean hasEnoughSkills(User user, Project project) {

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

    public static BidInfo addBid(Project project, Integer bidAmount) {
        User user = Commands.getDefaultUser();
        if (Commands.userIsBidBefore(project, user))
            throw new BadRequestException("User has already bidded!");
        if (bidAmount > project.getBudget())
            throw new BadRequestException("Bid amount is higher than project budget!");

        Auction auction = auctionRepository.getAuction(project.getId());
        if(auction == null) {
            auction = new Auction(project.getId());
            auctionRepository.insertAuction(auction);
        }
        BidInfo bidInfo = new BidInfo(user.getId(), project.getId(), bidAmount);
        auction.addOffer(bidInfo);
        return bidInfo;
    }

    private static boolean userIsBidBefore(Project project, User user){
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

    public static void addUserSkill(String skillName) {
        User user = Commands.getDefaultUser();
        Skill skill = new Skill(skillName, 0);
        if (!skillRepository.skillExists(skill))
            throw new BadRequestException("Skill " + skillName + " doesn't exist!");
        if (user.getSkills().indexOf(skill) != -1)
            throw new BadRequestException("User already has " + skillName + " skill!");
        userRepository.addUserSkill(user.getId(), skillName);
    }

    public static void deleteUserSkill(String skillName) {
        User user = Commands.getDefaultUser();
        Skill skill = new Skill(skillName, 0);
        if (!skillRepository.skillExists(skill))
            throw new BadRequestException("Skill " + skillName + " doesn't exist!");
        if (user.getSkills().indexOf(skill) == -1)
            throw new BadRequestException("User doesn't have " + skillName + " skill!");
        userRepository.deleteUserSkill(user.getId(), skillName);
    }

    public static Endorse endorseSkill(Integer endorsedId, String skillName) {
        Integer endorserId = Commands.getDefaultUser().getId();
        Endorse endorse = new Endorse(endorserId, endorsedId, skillName);
        User user = getUserById(endorsedId);
        if (endorserId.equals(endorsedId))
            throw new ForbiddenException("You can't endorse yourself!");
        if (endorseRepository.endorseExists(endorse))
            throw new BadRequestException("Already Endorsed!");
        if (user.getSkills().indexOf(new Skill(skillName, 0)) == -1)
            throw new BadRequestException("User doesn't have endorsed skill!");

        endorseRepository.insertEndorse(endorse);
        userRepository.updateUserSkillPoint(endorsedId, skillName, 1);
        return endorse;
    }

    public static List<EndorsableSkill> getUserEndorsableSkills(Integer endorserId, Integer endorsedId) {
        User endorsed = userRepository.getUserById(endorsedId);

        if (endorsedId.equals(endorserId))
            return endorsed.getSkills().stream()
                .map(skill -> new EndorsableSkill(skill, false))
                .collect(Collectors.toList());

        List<Endorse> endorses = endorseRepository.getEndorses(endorserId);
        List<EndorsableSkill> result = new LinkedList<>();
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
