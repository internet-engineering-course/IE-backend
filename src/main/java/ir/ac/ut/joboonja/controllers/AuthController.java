package ir.ac.ut.joboonja.controllers;

import ir.ac.ut.joboonja.entities.User;
import ir.ac.ut.joboonja.models.Token;
import ir.ac.ut.joboonja.services.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public Token login(@RequestBody User user) {
        return AuthService.authenticateUser(user);
    }
}
