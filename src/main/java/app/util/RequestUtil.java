package app.util;

import app.util.enums.*;
import io.javalin.http.Context;

public class RequestUtil
{
    public static String getQueryLocale(Context ctx) {
        return ctx.queryParam("locale");
    }

    public static String getParamIsbn(Context ctx) {
        return ctx.pathParam("isbn");
    }

    public static String getQueryEmail(Context ctx) {
        return ctx.formParam("email");
    }

    public static String getQueryPassword(Context ctx) {
        return ctx.formParam("password");
    }

    public static String getQueryLoginRedirect(Context ctx) {
        return ctx.queryParam("loginRedirect");
    }

    public static String getSessionLocale(Context ctx) {
        return (String) ctx.sessionAttribute("locale");
    }

    public static String getSessionCurrentUser(Context ctx) {
        return (String) ctx.sessionAttribute("currentUser");
    }

    public static boolean removeSessionAttrLoggedOut(Context ctx) {
        String loggedOut = ctx.sessionAttribute("loggedOut");
        ctx.sessionAttribute("loggedOut", null);
        return loggedOut != null;
    }

    public static String removeSessionAttrLoginRedirect(Context ctx) {
        String loginRedirect = ctx.sessionAttribute("loginRedirect");
        ctx.sessionAttribute("loginRedirect", null);
        return loginRedirect;
    }

    public static int getQueryIssueIdParam(Context ctx) {
        return Integer.parseInt(ctx.pathParam("id"));
    }

    public static String getQueryIssueName(Context ctx) {
        return ctx.formParam("name");
    }

    public static String getQueryIssueDescription(Context ctx) {
        return ctx.formParam("description");
    }

    public static String getQueryIssueSearchNameParam(Context ctx) {
        return ctx.queryParam("searchName");
    }

    public static CriticalType getQueryIssueCritical(Context ctx) {
        return CriticalType.getEnum(Integer.parseInt(ctx.formParam("critical")));
    }

    public static PriorityType getQueryIssuePriority(Context ctx) {
        return PriorityType.getEnum(Integer.parseInt(ctx.formParam("priority")));
    }

    public static String getQueryIssueSteps(Context ctx) {
        return ctx.formParam("steps");
    }

    public static String getQueryIssueExpected(Context ctx) {
        return ctx.formParam("expected");
    }

    public static String getQueryIssueActual(Context ctx) {
        return ctx.formParam("actual");
    }

    public static String getQueryIssueAssign(Context ctx) {
        return ctx.formParam("assign");
    }

    public static StatusType getQueryIssueStatus(Context ctx) {
        return StatusType.getEnum(Integer.parseInt(ctx.formParam("status")));
    }
}