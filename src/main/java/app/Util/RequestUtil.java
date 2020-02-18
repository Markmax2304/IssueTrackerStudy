package app.Util;

import io.javalin.http.Context;

public class RequestUtil
{
    public static String getSessionCurrentUser(Context ctx) {
        return (String) ctx.sessionAttribute("currentUser");
    }

    public static String getQueryIssueName(Context ctx) {
        return ctx.formParam("name");
    }

    public static String getQueryIssueDescription(Context ctx) {
        return ctx.formParam("description");
    }

    public static int getQueryIssueIdParam(Context ctx) {
        return Integer.parseInt(ctx.pathParam("id"));
    }

    public static String getQueryIssueSearchName(Context ctx) {
        return ctx.formParam("searchName");
    }
}
