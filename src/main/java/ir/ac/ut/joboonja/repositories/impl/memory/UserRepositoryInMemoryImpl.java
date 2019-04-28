package ir.ac.ut.joboonja.repositories.impl.memory;

import ir.ac.ut.joboonja.repositories.UserRepository;
import ir.ac.ut.joboonja.entities.Skill;
import ir.ac.ut.joboonja.entities.User;

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
    public void updateUserSkillPoint(Integer userId, String skillName, Integer points) {
        User user = MemoryDataBase.getInstance().getUser(userId);
        for (Skill skill: user.getSkills())
            if (skill.getName().equals(skillName)) {
                skill.setPoint(skill.getPoint() + points);
                return;
            }
    }

    @Override
    public void addUserSkill(Integer userId, String skillName) {
        MemoryDataBase.getInstance().addUserSkill(userId, skillName);
    }

    @Override
    public void deleteUserSkill(Integer userId, String skillName) {
        MemoryDataBase.getInstance().deleteUserSkill(userId, skillName);
    }

    @Override
    public List<User> getAllUser() {
        return MemoryDataBase.getInstance().getAllUser();
    }


}
