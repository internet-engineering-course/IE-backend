import database.MemoryDataBase;
import entities.Auction;
import entities.Project;
import entities.Skill;
import entities.User;
import models.BidInfo;
import models.ProjectTitle;
import utilities.Deserializer;

import java.io.IOException;

public class Commands {

    public static void register(String json){
        User user = Deserializer.deserialize(json , User.class);
        MemoryDataBase.getInstance().insertUser(user);

    }

    public static void addProject(String json){
        Project project = Deserializer.deserialize(json , Project.class);
        MemoryDataBase.getInstance().insertProject(project);
    }

    public static boolean addBid(String json){
        BidInfo bidInfo = Deserializer.deserialize(json , BidInfo.class);
        if (!meetsRequirements(bidInfo))
            return false;

        Auction auction = MemoryDataBase.getInstance().getAuction(bidInfo.getProjectTitle());
        if (auction == null) {
            auction = new Auction(bidInfo.getProjectTitle());
            MemoryDataBase.getInstance().insertAuction(auction);
        }
        auction.addOffer(bidInfo);
        return true;
    }

    private static boolean meetsRequirements(BidInfo bidInfo) {
        User user = MemoryDataBase.getInstance().getUser(bidInfo.getBiddingUser());
        Project project = MemoryDataBase.getInstance().getProject(bidInfo.getProjectTitle());
        if (user == null || project == null)
            return false;

        if(bidInfo.getBidAmount() > project.getBudget())
            return false;

        boolean meets = true;
        for (Skill skill: project.getSkills()) {
            int skillIndex = user.getSkills().indexOf(skill);
            if (skillIndex == -1)
                meets = false;
            else if (user.getSkills().get(skillIndex).getPoints() < skill.getPoints())
                meets = false;
        }

        return meets;
    }

    public static User auction(ProjectTitle projectTitle) {
        return null;
    }
}
