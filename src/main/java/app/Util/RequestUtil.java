package app.util;

import app.util.enums.*;
import io.javalin.http.Context;

public class RequestUtil
{
    public static String getSessionCurrentUser(Context ctx) {
        return (String) ctx.sessionAttribute("currentUser");
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
