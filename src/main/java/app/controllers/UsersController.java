package app.controllers;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
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
        if (newUser.save()) {
            // send confirmation to email
            //sendMail("http://localhost:7777/confirm?email="/*"http://t2.sumdu-tss.site/confirm?email="*/ + email,
            //        email);
            ctx.redirect("/");
        } else {
            errors.put("errors", newUser.errors());

            ctx.render(Path.Template.REGISTRATION, errors);
        }
    };

    public static void setPassword(String email, String oldPassword, String newPassword) {
        if (authenticate(email, oldPassword)) {
            String newSalt = BCrypt.gensalt();
            String newHashedPassword = BCrypt.hashpw(newPassword, newSalt);
        }
    }

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

    private static void sendMail(String link, String address){
        try
        {
            Email email = new SimpleEmail();
            email.setHostName("t2.sumdu-tss.site");
            email.setSmtpPort(7777);
            email.setSSLOnConnect(true);
            email.setSubject("Confirm registration of Trello-like"); // subject from HTML-form
            email.setMsg(link); // message from HTML-form
            email.addTo(address);
            email.send(); // will throw email-exception if something is wrong
        }
        catch (EmailException ex)
        {
        }
    }
}