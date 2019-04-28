package ir.ac.ut.joboonja.repositories.impl;

import ir.ac.ut.joboonja.entities.Skill;
import ir.ac.ut.joboonja.entities.User;
import ir.ac.ut.joboonja.repositories.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class UserRepositoryImpl extends JDBCRepository<User> implements UserRepository {
    @Override
    public boolean userExists(User user) {
        return false;
    }

    @Override
    public void insertUser(User user) {

    }

    @Override
    public void addUserSkill(Integer userId, String skillName) {

    }

    @Override
    public void deleteUserSkill(Integer userId, String skillName) {

    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public User getUserById(Integer id) {
        return null;
    }

    @Override
    public void updateUserSkillPoint(Integer userId, String skillName, Integer points) {

    }

    @Override
    public List<User> getAllUser() {
        return null;
    }

    @Override
    String getTableName() {
        return "User";
    }

    @Override
    User toDomainModel(ResultSet resultSet) throws SQLException {
        LinkedList<Skill> skills = new LinkedList<>();
        skills.add(new Skill(resultSet.getString("skillName"), resultSet.getInt("points")));
        return new User(
            resultSet.getInt("id"),
            resultSet.getString("username"),
            resultSet.getString("firstname"),
            resultSet.getString("lastname"),
            resultSet.getString("jobTitle"),
            resultSet.getString("bio"),
            skills
        );
    }
}
