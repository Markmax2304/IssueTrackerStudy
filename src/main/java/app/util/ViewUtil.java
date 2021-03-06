package app.util;

import io.javalin.http.Context;
import io.javalin.http.ErrorHandler;
import io.javalin.http.Handler;

import java.util.HashMap;
import java.util.Map;

import static app.util.RequestUtil.*;

public class ViewUtil {
    public static Map<String, Object> baseModel(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("msg", new MessageBundle(getSessionLocale(ctx)));
        model.put("currentUser", getSessionCurrentUserEmail(ctx));
        return model;
    }

    public static Handler getTermOfUse = ctx -> {
        ctx.render(Path.Template.USE_OF_TERMS, baseModel(ctx));
    };

    public static ErrorHandler notFound = ctx -> {
        ctx.render(Path.Template.NOT_FOUND, baseModel(ctx));
    };

    public static ErrorHandler serverError = ctx -> {
        ctx.render(Path.Template.SERVER_ERROR, baseModel(ctx));
    };
}
