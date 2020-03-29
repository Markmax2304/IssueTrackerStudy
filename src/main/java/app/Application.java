package app;

import app.util.ViewUtil;
import io.javalin.Javalin;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import static app.util.Route.*;
import static org.assertj.core.api.Assertions.assertThat;

public class Application {
    private static Javalin app;

    public static void main(String[] args) {
        app = Javalin.create(config -> {
            config.addStaticFiles("/public");
        }).start(7777);

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
