package ir.ac.ut.joboonja.controllers;

import ir.ac.ut.joboonja.command.Commands;
import ir.ac.ut.joboonja.entities.Endorse;
import ir.ac.ut.joboonja.entities.Skill;
import ir.ac.ut.joboonja.entities.User;
import ir.ac.ut.joboonja.exceptions.BadRequestException;
import ir.ac.ut.joboonja.exceptions.NotFoundException;
import ir.ac.ut.joboonja.models.EndorseRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public List<User> getUsers() {
        User user = Commands.getDefaultUser();
        return Commands.getAllUsers(user);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") String userId) {
        try {
            User user = Commands.getUserById(Integer.valueOf(userId));
            if (user == null)
                throw new NotFoundException("User not found");
            return user;
        } catch (NumberFormatException e) {
            throw new NotFoundException("User not found");
        }
    }

    // TODO user cannot endorse himself, skill should exist in user skills
    @PostMapping("/{userId}/endorse")
    public Endorse endorse(@PathVariable("userId") String userId, @RequestBody EndorseRequest endorseRequest) {
        Integer endorserId = Commands.getDefaultUser().getId();
        Integer endorsedId = Integer.valueOf(userId);
        Endorse endorse = Commands.endorseSkill(endorserId, endorsedId, endorseRequest.getSkillName());
        if (endorse == null)
            throw new BadRequestException("Already Endorsed");
        return endorse;
    }


    //todo: check that the skill is valid or not
    @PutMapping
    public User updateUser(@RequestBody User user) {
        User defaultUser = Commands.getDefaultUser();
        if (user.getSkills().size() != 0) {
            for (Skill skill: user.getSkills()) {
                Commands.updateUserSkill(defaultUser.getId(), skill.getName());
            }
        }
        return defaultUser;
    }

    @DeleteMapping
    public User deleteUserSkill(@RequestBody User user) {
        User defaultUser = Commands.getDefaultUser();
        if (user.getSkills().size() != 0) {
            for (Skill skill: user.getSkills()) {
                Commands.deleteUserSkill(defaultUser.getId(), skill.getName());
            }
        }
        return defaultUser;
    }
}
