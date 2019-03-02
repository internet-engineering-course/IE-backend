package database;

import entities.User;

import java.util.List;

public interface UserRepository {

    boolean userExists(User user);

    void insertUser(User user);

    User getUser(String username);
    User getUserById(Integer id);
    List<User> getAllUser();
}
