package ir.ac.ut.joboonja.repositories;

import ir.ac.ut.joboonja.entities.User;

import java.util.List;

public interface UserRepository {

    boolean userExists(User user);

    void insertUser(User user);
    void addUserSkill(Integer userId, String skillName);
    void deleteUserSkill(Integer userId, String skillName);

    User getUser(String username);
    User getUserById(Integer id);

    void updateUserSkillPoint(Integer userId, String skillName, Integer points);
    List<User> getAllUser();
}
