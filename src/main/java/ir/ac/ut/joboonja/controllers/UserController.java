package ir.ac.ut.joboonja.controllers;

import ir.ac.ut.joboonja.command.Commands;
import ir.ac.ut.joboonja.entities.User;
import ir.ac.ut.joboonja.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
