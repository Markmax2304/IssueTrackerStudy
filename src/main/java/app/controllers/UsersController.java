package app.controllers;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;
import org.mindrot.jbcrypt.BCrypt;
import static app.util.RequestUtil.*;
import app.models.User;
import java.util.Map;

public class UsersController {

    public static Handler createNewUser = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Path.Template.REGISTRATION, model);
    };

    public static Handler addUser = ctx -> {
        Map<String, Object> errors = ViewUtil.baseModel(ctx);

        String name = getQueryUserName(ctx);
        String email = getQueryEmail(ctx);
        String password = getQueryPassword(ctx);

        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(password, salt);

        User newUser = new User(name, email, hashedPassword);
        newUser.set("salt", salt);
        if (newUser.save())
            ctx.redirect("/issues");
        else{
            errors.put("errors", newUser.errors());

            ctx.render(Path.Template.REGISTRATION, errors);
        }
    };

    public static boolean authenticate(String email, String password) {
        if (email == null || password == null) {
            return false;
        }
        User user = User.findFirst("email = ?", email);
        if (user == null) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(password, user.getString("salt"));
        return hashedPassword.equals(user.getString("password"));
    }

    public static void setPassword(String email, String oldPassword, String newPassword) {
        if (authenticate(email, oldPassword)) {
            String newSalt = BCrypt.gensalt();
            String newHashedPassword = BCrypt.hashpw(newPassword, newSalt);
        }
    }
}