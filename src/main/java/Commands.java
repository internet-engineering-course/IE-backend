import database.MemoryDataBase;
import entities.Project;
import entities.User;
import models.BidInfo;
import models.ProjectTitle;
import org.codehaus.jackson.map.ObjectMapper;
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

    public static boolean addBid(BidInfo bidInfo){
        return true;
    }

    public static User auction(ProjectTitle projectTitle) {
        return null;
    }
}
