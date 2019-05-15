package ir.ac.ut.joboonja.controllers;

import ir.ac.ut.joboonja.entities.Endorse;
import ir.ac.ut.joboonja.entities.Skill;
import ir.ac.ut.joboonja.entities.User;
import ir.ac.ut.joboonja.models.EndorsableSkill;
import ir.ac.ut.joboonja.models.EndorseRequest;
import ir.ac.ut.joboonja.services.EndorseService;
import ir.ac.ut.joboonja.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public List<User> getUsers() {
        return UserService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Integer userId) {
        return UserService.getUserById(userId);
    }

    @GetMapping("/{userId}/endorse")
    public List<EndorsableSkill> getEndorsableSkills(@PathVariable("userId") Integer userId) {
        return EndorseService.getUserEndorsableSkills(UserService.getDefaultUser().getId(), userId);
    }

    @PostMapping("/{userId}/endorse")
    public Endorse endorse(@PathVariable("userId") Integer userId, @RequestBody EndorseRequest endorseRequest) {
        return EndorseService.endorseSkill(userId, endorseRequest.getSkillName());
    }

    @PostMapping
    public User insertUser(@RequestBody User user){
          return UserService.insertUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (user.getSkills().size() != 0) {
            for (Skill skill: user.getSkills()) {
                UserService.addUserSkill(skill.getName());
            }
        }
        return UserService.getDefaultUser();
    }

    @DeleteMapping
    public User deleteUserSkill(@RequestBody User user) {
        if (user.getSkills().size() != 0) {
            for (Skill skill: user.getSkills()) {
                UserService.deleteUserSkill(skill.getName());
            }
        }
        return UserService.getDefaultUser();
    }

    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam(name = "filter", required = false) String filter) {
        if (filter == null || filter.isEmpty())
            return Collections.emptyList();

        return UserService.searchUsers(filter);
    }
}
