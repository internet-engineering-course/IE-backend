package ir.ac.ut.joboonja.services;

import ir.ac.ut.joboonja.auth.JWTUtils;
import ir.ac.ut.joboonja.entities.User;
import ir.ac.ut.joboonja.exceptions.ForbiddenException;
import ir.ac.ut.joboonja.exceptions.NotFoundException;
import ir.ac.ut.joboonja.models.Token;
import org.apache.commons.codec.digest.DigestUtils;

public class AuthService {

    public static Token authenticateUser(User user) {
        if (user.getUsername() == null || user.getPassword() == null)
            throw new ForbiddenException("Username or password cannot be empty!");
        User registeredUser = null;
        try {
            registeredUser = UserService.getUserByUserName(user.getUsername());
        } catch (NotFoundException e) {
            throw new ForbiddenException("Username is invalid!");
        }
        if (!DigestUtils.sha256Hex(user.getPassword().getBytes()).equals(registeredUser.getPassword()))
            throw new ForbiddenException("Password is invalid!");

        return new Token(JWTUtils.createJWT(user.getUsername()));
    }
}
