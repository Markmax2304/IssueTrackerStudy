package app.controllers;

import app.models.Comment;
import app.util.enums.CriticalType;
import app.util.enums.PriorityType;
import app.util.enums.StatusType;
import app.util.Path;
import app.util.ViewUtil;
import app.models.Issue;
import app.models.User;
import io.javalin.http.Handler;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

import static app.util.RequestUtil.*;

public class IssueController
{
    public static Handler fetchAllIssues = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);

        List<Issue> issues = Issue.findAll();
        model.put("issues", issues);

        ctx.render(Path.Template.ISSUES, model);
    };

    public static Handler searchIssueByName = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);

        String name = getQueryIssueSearchNameParam(ctx);
        // TODO: try to use 'like' statement
        List<Issue> issue = Issue.where("name = ?", name);
        model.put("issues", issue);

        ctx.render(Path.Template.ISSUES, model);
    };

    public static Handler createNewIssue = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Path.Template.ISSUES_ADD, model);
    };

    public static Handler addIssue = ctx -> {
        try
        {
            String name = getQueryIssueName(ctx);
            if (StringUtils.isEmpty(name))
                throw new Exception("Can't add issue with empty name");

            String description = getQueryIssueDescription(ctx);
            CriticalType critical = getQueryIssueCritical(ctx);
            PriorityType priority = getQueryIssuePriority(ctx);
            String steps = getQueryIssueSteps(ctx);
            String expected = getQueryIssueExpected(ctx);
            String actual = getQueryIssueActual(ctx);
            StatusType status = getQueryIssueStatus(ctx);

            String userEmail = ctx.sessionAttribute("currentUser");
            User currentUser = User.findFirst("email = ?", userEmail);
            // if currentUser is null
            int authorId = 1;
            if(currentUser != null){
                authorId = (int)currentUser.getId();
            }

            Issue newIssue = new Issue(name, description, critical, priority, steps,
                    expected, actual, status, authorId);

            int assignId = getQueryIssueAssignId(ctx);
            if (User.exists(assignId)) {
                newIssue.set("executor_id", assignId);
            }

            newIssue.saveIt();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        finally {
            ctx.redirect("/issues");
        }
    };

    public static Handler fetchOneIssue = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);

        int issueId = getQueryIssueIdParam(ctx);
        Issue issue = Issue.findById(issueId);
        model.put("oneIssue", issue);

        List<Comment> comments = Comment.find("issue_id = ?", issueId);
        // TODO: sort list by created time
        model.put("comments", comments);

        List<User> users = User.findAll();
        model.put("users", users);

        ctx.render(Path.Template.ISSUES_UPDATE, model);
    };

    public static Handler deleteIssue = ctx -> {

        int issueId = getQueryIssueIdParam(ctx);
        Issue issue = Issue.findById(issueId);
        issue.delete();

        ctx.redirect("/issues");
    };

    public static Handler changeIssue = ctx -> {
        try
        {
            int issueId = getQueryIssueIdParam(ctx);
            Issue changedIssue = Issue.findById(issueId);

            String name = getQueryIssueName(ctx);
            String description = getQueryIssueDescription(ctx);
            CriticalType critical = getQueryIssueCritical(ctx);
            PriorityType priority = getQueryIssuePriority(ctx);
            String steps = getQueryIssueSteps(ctx);
            String expected = getQueryIssueExpected(ctx);
            String actual = getQueryIssueActual(ctx);
            StatusType status = getQueryIssueStatus(ctx);

            if(StringUtils.isEmpty(name))
                throw new Exception("Can't add issue with empty name");

            changedIssue.set(
                "name", name,
                "description", description,
                "critical", critical.getValue(),
                "priority", priority.getValue(),
                "steps", steps,
                "expected", expected,
                "actual", actual,
                "status", status.getValue()
            );

            int assignId = getQueryIssueAssignId(ctx);
            if (User.exists(assignId)) {
                changedIssue.set("executor_id", assignId);
            }

            changedIssue.saveIt();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        finally {
            ctx.redirect("/issues");
        }
    };

    public static Handler addCommentToIssue = ctx -> {
        int issueId = getQueryIssueIdParam(ctx);
        String commentMessage = getQueryIssueCommentMessage(ctx);

        String userEmail = ctx.sessionAttribute("currentUser");
        User currentUser = User.findFirst("email = ?", userEmail);
        // if currentUser is null
        int userId = 1;
        if(currentUser != null){
            userId = (int)currentUser.getId();
        }

        Comment comment = new Comment(commentMessage, userId, issueId);
        comment.saveIt();

        ctx.redirect("/issues/" + issueId);
    };
}
