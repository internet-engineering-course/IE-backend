package database;

import entities.User;

import java.util.List;

public interface UserRepository {

    boolean userExists(User user);

    void insertUser(User user);
    void updateUserSkill(Integer userId, String skillName);
    void deleteUserSkill(Integer userId, String skillName);

    User getUser(String username);
    User getUserById(Integer id);

    void updateUserSkillPoint(Integer endorsedId, String skillName, Integer points);
    List<User> getAllUser();
}
