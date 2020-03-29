package app.util;

import app.controllers.IssueController;
import app.controllers.LoginController;
import app.controllers.ProjectController;
import app.controllers.UsersController;
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
        get("/", ProjectController.fetchAllProjects);       // just test
        get(Path.Web.PROJECTS, ProjectController.fetchAllProjects);
        get(Path.Web.PROJECTS_NEW, ProjectController.createNewProject);
        get(Path.Web.ISSUES, IssueController.fetchProjectIssues);
        get(Path.Web.ISSUE_NEW, IssueController.createNewIssue);
        get(Path.Web.ISSUE_SEARCH, IssueController.searchIssueByName);
        get(Path.Web.ISSUE_ONE, IssueController.fetchOneIssue);
        post(Path.Web.PROJECTS_ADD, ProjectController.addProject);
        post(Path.Web.PROJECTS_DELETE, ProjectController.deleteProject);
        post(Path.Web.PROJECTS_ADD_USER, ProjectController.addUserToProject);
        post(Path.Web.PROJECTS_REMOVE_USER, ProjectController.removeUserFromProject);
        post(Path.Web.ISSUE_UPDATE, IssueController.changeIssue);
        post(Path.Web.ISSUE_DELETE, IssueController.deleteIssue);
        post(Path.Web.ISSUE_COMMENT, IssueController.addCommentToIssue);
        post(Path.Web.ISSUE_ADD, IssueController.addIssue);
        get("/users/new", UsersController.createNewUser);
        post("/users/add", UsersController.addUser);

        after(ctx -> {Base.close();});
    };
}
