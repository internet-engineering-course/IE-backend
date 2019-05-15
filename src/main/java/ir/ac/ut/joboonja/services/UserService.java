package ir.ac.ut.joboonja.services;

import ir.ac.ut.joboonja.entities.Skill;
import ir.ac.ut.joboonja.entities.User;
import ir.ac.ut.joboonja.exceptions.BadRequestException;
import ir.ac.ut.joboonja.exceptions.NotFoundException;
import ir.ac.ut.joboonja.repositories.UserRepository;
import ir.ac.ut.joboonja.repositories.impl.UserRepositoryImpl;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static UserRepository userRepository = new UserRepositoryImpl();

    public static User getDefaultUser(){
        return userRepository.getUser("ali");
    }

    public static User getUserById(Integer id){
        User user = userRepository.getUserById(id);
        if (user == null)
            throw new NotFoundException("User not found");
        return user;
    }

    public static List<User> getAllUsers() {
        User user = getDefaultUser();
        List<User> users = userRepository.getAllUsers();
        List<User> newList = new ArrayList<>(users);
        for(User u:newList){
            if(u.getId().equals(user.getId())){
                newList.remove(u);
                break;
            }
        }
        return newList;
    }

    public static void addUserSkill(String skillName) {
        User user = getDefaultUser();
        Skill skill = new Skill(skillName, 0);
        if (!SkillService.skillExists(skill))
            throw new BadRequestException("Skill " + skillName + " doesn't exist!");
        if (user.getSkills().indexOf(skill) != -1)
            throw new BadRequestException("User already has " + skillName + " skill!");
        userRepository.addUserSkill(user.getId(), skillName);
    }

    public static void deleteUserSkill(String skillName) {
        User user = getDefaultUser();
        Skill skill = new Skill(skillName, 0);
        if (!SkillService.skillExists(skill))
            throw new BadRequestException("Skill " + skillName + " doesn't exist!");
        if (user.getSkills().indexOf(skill) == -1)
            throw new BadRequestException("User doesn't have " + skillName + " skill!");
        userRepository.deleteUserSkill(user.getId(), skillName);
    }

    public static List<User> searchUsers(String filter) {
        return userRepository.searchUsers(filter);
    }

    public static User insertUser(User newUser) {
        newUser.setPassword(DigestUtils.sha256Hex(newUser.getPassword().getBytes()));
        userRepository.insertUser(newUser);
        return newUser;
    }

    public static void updateUserSkillPoint(Integer endorsedId, String skillName, int points) {
        userRepository.updateUserSkillPoint(endorsedId, skillName, points);
    }
}
