package app.controllers;

import app.models.Comment;
import app.models.Project;
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
    public static Handler fetchProjectIssues = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);

        int projectId = getQueryIdParam(ctx);
        Project project = Project.findById(projectId);
        model.put("project", project);

        List<Issue> issues = Issue.find("project_id = ?", projectId);
        model.put("issues", issues);

        List<User> projectUsers = project.getAll(User.class);
        model.put("projectUsers", projectUsers);

        List<User> otherUsers = User.findAll();
        for (int i = 0; i < projectUsers.size(); i++){
            for(int j = 0; j < otherUsers.size(); j++){
                if(projectUsers.get(i).getId() == otherUsers.get(j).getId()){
                    otherUsers.remove(j);
                }
            }
        }
        model.put("otherUsers", otherUsers);

        ctx.render(Path.Template.ISSUES, model);
    };

    public static Handler searchIssueByName = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);

        int projectId = getQueryIdParam(ctx);
        Project project = Project.findById(projectId);
        model.put("project", project);

        List<User> projectUsers = project.getAll(User.class);
        model.put("projectUsers", projectUsers);

        List<User> otherUsers = User.findAll();
        for (int i = 0; i < projectUsers.size(); i++){
            for(int j = 0; j < otherUsers.size(); j++){
                if(projectUsers.get(i).getId() == otherUsers.get(j).getId()){
                    otherUsers.remove(j);
                }
            }
        }
        model.put("otherUsers", otherUsers);

        String name = getQueryIssueSearchNameParam(ctx);
        List<Issue> issue = Issue.findBySQL("select issues.* from issues where (issues.name like '%" + name + "%' or " +
                "issues.description like '%" + name + "%') and issues.project_id = " + projectId);
        model.put("issues", issue);

        ctx.render(Path.Template.ISSUES, model);
    };

    public static Handler createNewIssue = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);

        int projectId = getQueryProjectIdParam(ctx);
        Project project = Project.findById(projectId);
        model.put("currentProject", project);

        ctx.render(Path.Template.ISSUES_ADD, model);
    };

    public static Handler addIssue = ctx -> {

        int projectId = getQueryIssueProjectId(ctx);

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
            int authorId = (int)currentUser.getId();

            Issue newIssue = new Issue(name, description, critical, priority, steps,
                    expected, actual, status, authorId, projectId);

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
            ctx.redirect("/projects/" + projectId);
        }
    };

    public static Handler fetchOneIssue = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);

        int issueId = getQueryIdParam(ctx);
        Issue issue = Issue.findById(issueId);
        model.put("oneIssue", issue);

        List<Comment> comments = Comment.find("issue_id = ?", issueId);
        // TODO: sort list by created time
        model.put("comments", comments);

        // assigned users
        List<User> users = User.findAll();
        model.put("users", users);

        ctx.render(Path.Template.ISSUES_UPDATE, model);
    };

    public static Handler deleteIssue = ctx -> {

        int issueId = getQueryIdParam(ctx);
        Issue issue = Issue.findById(issueId);
        int projectId = issue.getProjectId();
        issue.delete(true);

        ctx.redirect("/projects/" + projectId);
    };

    public static Handler changeIssue = ctx -> {

        int projectId = -1;

        try
        {
            int issueId = getQueryIdParam(ctx);
            Issue changedIssue = Issue.findById(issueId);
            projectId = changedIssue.getProjectId();

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
            ctx.redirect(projectId == -1 ? Path.Web.PROJECTS : "/projects/" + projectId);
        }
    };

    public static Handler addCommentToIssue = ctx -> {
        int issueId = getQueryIdParam(ctx);
        String commentMessage = getQueryIssueCommentMessage(ctx);

        String userEmail = ctx.sessionAttribute("currentUser");
        User currentUser = User.findFirst("email = ?", userEmail);

        Comment comment = new Comment(commentMessage, (int)currentUser.getId(), issueId);
        comment.saveIt();

        ctx.redirect("/issues/one/" + issueId);
    };
}
