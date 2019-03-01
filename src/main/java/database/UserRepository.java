package database;

import entities.User;

public interface UserRepository {

    boolean userExists(User user);

    void insertUser(User user);
    void updateUserSkill(Integer userId, String skillName);
    void deleteUserSkill(Integer userId, String skillName);

    User getUser(String username);
    User getUserById(Integer id);
}
