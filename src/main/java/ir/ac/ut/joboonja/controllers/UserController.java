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
    public List<User> getUsers(@RequestAttribute("user") User user) {
        return UserService.getAllUsers(user);
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username) {
        return UserService.getUserByUserName(username);
    }

    @GetMapping("/{username}/endorse")
    public List<EndorsableSkill> getEndorsableSkills(@PathVariable("username") String username, @RequestAttribute("user") User user) {
        User tempUser = UserService.getUserByUserName(username);
        return EndorseService.getUserEndorsableSkills(user.getId(), tempUser.getId());
    }

    @PostMapping("/{username}/endorse")
    public Endorse endorse(@PathVariable("username") String username, @RequestBody EndorseRequest endorseRequest, @RequestAttribute("user") User user) {
        User tempUser = UserService.getUserByUserName(username);
        return EndorseService.endorseSkill(tempUser.getId(), endorseRequest.getSkillName(), user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user, @RequestAttribute("user") User loggedInUser) {
        if (user.getSkills().size() != 0) {
            for (Skill skill: user.getSkills()) {
                UserService.addUserSkill(skill.getName(), loggedInUser);
            }
        }
        return UserService.getUserByUserName(loggedInUser.getUsername());
    }

    @DeleteMapping
    public User deleteUserSkill(@RequestBody User user, @RequestAttribute("user") User loggedInUser) {
        if (user.getSkills().size() != 0) {
            for (Skill skill: user.getSkills()) {
                UserService.deleteUserSkill(skill.getName(), loggedInUser);
            }
        }
        return UserService.getUserById(loggedInUser.getId());
    }

    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam(name = "filter", required = false) String filter, @RequestAttribute("user") User user) {
        if (filter == null || filter.isEmpty())
            return Collections.emptyList();

        return UserService.searchUsers(filter, user);
    }
}
