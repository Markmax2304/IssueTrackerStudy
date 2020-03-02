package app;

import app.util.ViewUtil;
import io.javalin.Javalin;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import static app.util.Route.*;
import static org.assertj.core.api.Assertions.assertThat;
import app.models.User;
import org.javalite.activejdbc.Base;
import org.mindrot.jbcrypt.BCrypt;

public class Application {
    private static Javalin app;

    public static void main(String[] args) {
        app = Javalin.create().start(80);

        Base.open();
        User user = User.findFirst("email = ?", "test5@gmail.com");
        if(user == null)
        {
            User testUser = new User("test name", "test5@gmail.com", BCrypt.hashpw("123456", "$2a$08$/w47HuWAfnuH6RPw7cuO9e"));
            testUser.saveIt();
        }
        Base.close();

        app.routes(routes);

        app.error(404, ViewUtil.notFound);

        /*Test_GetAllIssues();
        Test_CreateNewIssue();
        Test_CreateNewIssueWithEmptyName();
        Test_UpdateIssue();*/
    }

    private static void Test_GetAllIssues() {
        HttpResponse response = Unirest.get("http://localhost:7777/issues").asString();
        assertThat(response.getStatus()).isEqualTo(200);
    }

    private static void Test_CreateNewIssue() {
        HttpResponse response = Unirest.get("http://localhost:7777/issues/add").asString();
        assertThat(response.getStatus()).isEqualTo(200);

        response = Unirest.post("http://localhost:7777/issues/add")
                .field("name", "Func Test")
                .field("description", "It is just a integration test").asString();
        assertThat(response.getStatus()).isEqualTo(302);
    }

    private static void Test_CreateNewIssueWithEmptyName() {
        HttpResponse response = Unirest.get("http://localhost:7777/issues/add").asString();
        assertThat(response.getStatus()).isEqualTo(200);

        response = Unirest.post("http://localhost:7777/issues/add")
                .field("name", "")
                .field("description", "It is just a integration test").asString();
        assertThat(response.getStatus()).isEqualTo(500);
    }

    private static void Test_UpdateIssue() {
        HttpResponse response = Unirest.get("http://localhost:7777/issues/update/1").asString();
        assertThat(response.getStatus()).isEqualTo(200);

        response = Unirest.post("http://localhost:7777/issues/update/change/1")
                .field("name", "New name for bug")
                .field("description", "Updated description for first bug").asString();
        assertThat(response.getStatus()).isEqualTo(302);
    }
}
