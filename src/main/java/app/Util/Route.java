package app.util;

import app.controllers.IssueController;
import io.javalin.apibuilder.EndpointGroup;
import org.javalite.activejdbc.Base;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Route
{
    public static EndpointGroup routes = () ->  {
        before(ctx -> {Base.open();});

        get("/", IssueController.fetchAllIssues);       // just test
        get("/issues", IssueController.fetchAllIssues);
        get("/issues/new", IssueController.createNewIssue);
        get("/issues/search", IssueController.searchIssueByName);
        get("/issues/:id", IssueController.fetchOneIssue);
        post("/issues/update/:id", IssueController.changeIssue);
        post("/issues/delete/:id", IssueController.deleteIssue);
        post("/issues/add", IssueController.addIssue);

        after(ctx -> {Base.close();});
    };
}
