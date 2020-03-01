package app.util;

import app.controllers.IssueController;
import app.controllers.LoginController;
import io.javalin.apibuilder.EndpointGroup;
import org.javalite.activejdbc.Base;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Route
{
    public static EndpointGroup routes = () ->  {
        before(ctx -> {Base.open();});
        before(Filters.handleLocaleChange);
        before(LoginController.ensureLoginBefore);

        get(Path.Web.LOGIN, LoginController.serveLoginPage);
        post(Path.Web.LOGIN, LoginController.handleLoginPost);
        post(Path.Web.LOGOUT, LoginController.handleLogoutPost);
        get("/", IssueController.fetchAllIssues);       // just test
        get("/issues", IssueController.fetchAllIssues);
        get("/issues/new", IssueController.createNewIssue);
        get("/issues/search", IssueController.searchIssueByName);
        get("/issues/:id", IssueController.fetchOneIssue);
        post("/issues/update/:id", IssueController.changeIssue);
        post("/issues/delete/:id", IssueController.deleteIssue);
        post("/issues/comment/:id", IssueController.addCommentToIssue);
        post("/issues/add", IssueController.addIssue);

        after(ctx -> {Base.close();});
    };
}
