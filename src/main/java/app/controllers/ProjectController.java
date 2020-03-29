package app.controllers;

import app.models.Issue;
import app.models.Project;
import app.models.User;
import app.util.Path;
import app.util.ViewUtil;
import app.util.enums.AccessType;
import io.javalin.http.Handler;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static app.util.RequestUtil.*;

public class ProjectController
{
    public static Handler fetchAllProjects = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);

        String currentUserEmail = ctx.sessionAttribute("currentUser");
        User currentUser = User.findFirst("email = ?", currentUserEmail);

        List<Project> projects = currentUser.getAll(Project.class);
        List<Project> publicProjects = Project.find("access = ?", 1);
        projects = projects.stream().filter(p -> p.getAccessString().equals(AccessType.Private.toString()))
                .collect(Collectors.toList());
        projects.addAll(publicProjects);

        model.put("projects", projects);

        ctx.render(Path.Template.PROJECTS, model);
    };

    public static Handler createNewProject = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Path.Template.PROJECTS_ADD, model);
    };

    public static Handler addProject = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);

        String name = getQueryProjectName(ctx);
        AccessType access = getQueryProjectAccess(ctx);
        if (StringUtils.isEmpty(name))
            name = "Unknown";

        String userEmail = getSessionCurrentUserEmail(ctx);
        User user = User.findFirst("email = ?", userEmail);

        Project newProject = new Project(name, (int)user.getId(), access);
        newProject.saveIt();
        newProject.add(user);

        ctx.redirect(Path.Web.PROJECTS);
    };

    public static Handler deleteProject = ctx -> {
        int projectId = getQueryIdParam(ctx);
        Project project = Project.findById(projectId);

        List<User> users = project.getAll(User.class);
        for (int i = 0; i < users.size(); i++)
        {
            project.remove(users.get(i));
        }

        List<Issue> issues = Issue.find("project_id = ?", project.getId());
        for(int i = 0; i < issues.size(); i++){
            issues.get(i).delete(true);
        }

        project.delete();

        ctx.redirect(Path.Web.PROJECTS);
    };

    public static Handler addUserToProject = ctx -> {
        int projectId = getQueryIdParam(ctx);
        int userId = getQueryAddUserId(ctx);

        Project project = Project.findById(projectId);
        User user = User.findById(userId);

        project.add(user);

        ctx.redirect("/projects/" + projectId);
    };

    public static Handler removeUserFromProject = ctx -> {
        int projectId = getQueryIdParam(ctx);
        int userId = getQueryRemoveUserId(ctx);

        Project project = Project.findById(projectId);
        User user = User.findById(userId);

        project.remove(user);

        // if any users isn't attached to project, project will be removed
        List<User> projectUsers = project.getAll(User.class);
        if(projectUsers == null || projectUsers.size() == 0)
        {
            List<Issue> issues = Issue.find("project_id = ?", project.getId());
            for(int i = 0; i < issues.size(); i++){
                issues.get(i).delete(true);
            }

            project.delete();
            ctx.redirect(Path.Web.PROJECTS);
        }
        else
        {
            ctx.redirect("/projects/" + projectId);
        }
    };
}
