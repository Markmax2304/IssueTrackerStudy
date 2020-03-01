package app.util;

import io.javalin.http.Context;
import io.javalin.http.ErrorHandler;

import java.util.HashMap;
import java.util.Map;

public class ViewUtil
{
    public static Map<String, Object> getBaseModel(Context ctx){
        Map<String, Object> model = new HashMap<>();
        //model.put("msg", new MessageBundle(getSessionLocale(ctx)));
        //model.put("currentUser", RequestUtil.getSessionCurrentUser(ctx));
        return model;
    }

    public static ErrorHandler notFound = ctx -> {
        ctx.render(Path.Template.NOT_FOUND, getBaseModel(ctx));
    };
}
