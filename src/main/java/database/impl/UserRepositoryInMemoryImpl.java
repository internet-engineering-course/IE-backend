package database.impl;

import database.UserRepository;
import entities.Skill;
import entities.User;

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
    public void updateUserSkillPoint(Integer endorsedId, String skillName, Integer points) {
        User user = MemoryDataBase.getInstance().getUser(endorsedId);
        for (Skill skill: user.getSkills())
            if (skill.getName().equals(skillName)) {
                skill.setPoint(skill.getPoint() + points);
                return;
            }
    }

    @Override
    public void updateUserSkill(Integer userId, String skillName) {
        MemoryDataBase.getInstance().updateUserSkill(userId, skillName);
    }

    @Override
    public void deleteUserSkill(Integer userId, String skillName) {
        MemoryDataBase.getInstance().deleteUserSkill(userId, skillName);
    }
}
