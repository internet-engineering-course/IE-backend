package database;

import entities.User;

public interface UserRepository {

    boolean userExists(User user);

    void insertUser(User user);

    User getUser(String username);
    User getUserById(Integer id);
}
