package app.controllers;

import io.javalin.http.Handler;
import java.util.Map;

import app.util.Path;
import app.util.ViewUtil;

import static app.util.RequestUtil.*;

public class LoginController {
    public static Handler serveLoginPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("loggedOut", removeSessionAttrLoggedOut(ctx));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(ctx));
        ctx.render(Path.Template.LOGIN, model);
    };

    public static Handler handleLoginPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if (!UsersController.authenticate(getQueryEmail(ctx), getQueryPassword(ctx))) {
            model.put("authenticationFailed", true);
            ctx.render(Path.Template.LOGIN, model);
        } else {
            ctx.sessionAttribute("currentUser", getQueryEmail(ctx));
            model.put("authenticationSucceeded", true);
            model.put("currentUser", getQueryEmail(ctx));
            if (getQueryLoginRedirect(ctx) != null) {
                ctx.redirect(getQueryLoginRedirect(ctx));
            }
            ctx.redirect(Path.Web.PROJECTS);
        }
    };

    public static Handler handleLogoutPost = ctx -> {
        ctx.sessionAttribute("currentUser", null);
        ctx.sessionAttribute("loggedOut", "true");
        ctx.redirect(Path.Web.LOGIN);
    };

    // The origin of the request (request.pathInfo()) is saved in the session so
    // the user can be redirected back after login
    public static Handler ensureLoginBefore = ctx -> {
        if (ctx.path().startsWith(Path.Web.LOGIN) || ctx.path().startsWith("/users") ||
            ctx.path().startsWith("/img") || ctx.path().startsWith("/robots") || ctx.path().startsWith("/term")) {
            return;
        }
        if (ctx.sessionAttribute("currentUser") == null) {
            ctx.sessionAttribute("loginRedirect", ctx.path());
            ctx.redirect(Path.Web.LOGIN);
        }
    };

}
