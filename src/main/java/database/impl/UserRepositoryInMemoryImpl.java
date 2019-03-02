package database.impl;

import database.UserRepository;
import entities.User;

import java.util.List;

public class UserRepositoryInMemoryImpl implements UserRepository {


    @Override
    public boolean userExists(User user) {
        return MemoryDataBase.getInstance().userExists(user);
    }

    @Override
    public void insertUser(User user) {
        MemoryDataBase.getInstance().insertUser(user);
    }

    @Override
    public User getUser(String username) {
        return MemoryDataBase.getInstance().getUser(username);
    }

    @Override
    public User getUserById(Integer id) {
        return MemoryDataBase.getInstance().getUser(id);
    }

    @Override
    public List<User> getAllUser() {
        return MemoryDataBase.getInstance().getAllUser();
    }


}
