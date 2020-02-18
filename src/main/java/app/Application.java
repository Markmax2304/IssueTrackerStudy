package app;

import app.DB.DBConnector;
import app.Issue.Issue;
import app.Issue.IssueController;
import app.Issue.IssueDao;
import app.Util.Path;
import app.Util.ViewUtil;
import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJson;
import kong.unirest.GenericType;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.before;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

public class Application
{
    private static Javalin app;
    public static IssueDao issueDao;

    public static void main(String[] args)
    {
        try
        {
            DBConnector.Connection();
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        issueDao = new IssueDao();

        app = Javalin.create().start(7777);

        app.routes(() -> {
            get("/", IssueController.fetchAllIssues);       // just test
            get(Path.Web.ISSUES, IssueController.fetchAllIssues);
            get(Path.Web.ISSUE_ADD, IssueController.createNewIssue);
            get(Path.Web.ISSUE_UPDATE, IssueController.fetchOneIssue);
            get(Path.Web.ISSUE_DELETE, IssueController.deleteIssue);

            post(Path.Web.ISSUE_SEARCH, IssueController.searchIssueByName);
            post(Path.Web.ISSUE_ADD, IssueController.addIssue);
            post(Path.Web.ISSUE_CHANGE, IssueController.changeIssue);
        });

        app.error(404, ViewUtil.notFound);

        /*Test_GetAllIssues();
        Test_CreateNewIssue();
        Test_CreateNewIssueWithEmptyName();
        Test_UpdateIssue();*/
    }

    private static void Test_GetAllIssues()
    {
        HttpResponse response = Unirest.get("http://localhost:7777/issues").asString();
        assertThat(response.getStatus()).isEqualTo(200);
    }

    private static void Test_CreateNewIssue()
    {
        HttpResponse response = Unirest.get("http://localhost:7777/issues/add").asString();
        assertThat(response.getStatus()).isEqualTo(200);

        response = Unirest.post("http://localhost:7777/issues/add")
                .field("name", "Func Test")
                .field("description", "It is just a integration test").asString();
        assertThat(response.getStatus()).isEqualTo(302);
    }

    private static void Test_CreateNewIssueWithEmptyName()
    {
        HttpResponse response = Unirest.get("http://localhost:7777/issues/add").asString();
        assertThat(response.getStatus()).isEqualTo(200);

        response = Unirest.post("http://localhost:7777/issues/add")
                .field("name", "")
                .field("description", "It is just a integration test").asString();
        assertThat(response.getStatus()).isEqualTo(500);
    }

    private static void Test_UpdateIssue()
    {
        HttpResponse response = Unirest.get("http://localhost:7777/issues/update/1").asString();
        assertThat(response.getStatus()).isEqualTo(200);

        response = Unirest.post("http://localhost:7777/issues/update/change/1")
                .field("name", "New name for bug")
                .field("description", "Updated description for first bug").asString();
        assertThat(response.getStatus()).isEqualTo(302);
    }
}
