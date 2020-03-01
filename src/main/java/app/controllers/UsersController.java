package app.controllers;

import org.mindrot.jbcrypt.BCrypt;

import static app.Application.*;
import app.models.User;

public class UsersController {

    // Authenticate the user by hashing the inputted password using the stored salt,
    // then comparing the generated hashed password to the stored hashed password
    public static boolean authenticate(String email, String password) {
        if (email == null || password == null) {
            return false;
        }
        User user = User.findFirst("email = ?", email);
        if (user == null) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(password, "$2a$08$/w47HuWAfnuH6RPw7cuO9e");
//        String hashedPassword = user.getString("password");
        return hashedPassword.equals(user.getString("password"));
    }

    // This method doesn't do anything, it's just included as an example
    public static void setPassword(String username, String oldPassword, String newPassword) {
        if (authenticate(username, oldPassword)) {
            String newSalt = BCrypt.gensalt();
            String newHashedPassword = BCrypt.hashpw(newSalt, newPassword);
            // Update the user salt and password
        }
    }
}