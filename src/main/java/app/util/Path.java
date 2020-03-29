package app.util;

public class Path
{
    public static class Template {
        public static final String LOGIN = "/velocity/login/login.vm";
        public static final String REGISTRATION = "/velocity/users/registration.vm";
        public static final String PROJECTS = "/velocity/issue/projects.vm";
        public static final String PROJECTS_ADD = "/velocity/issue/add-projects.vm";
        public static final String ISSUES = "/velocity/issue/one-project.vm";
        public static final String ISSUES_ADD = "/velocity/issue/add-issue.vm";
        public static final String ISSUES_UPDATE = "/velocity/issue/one-issue.vm";
        public static final String NOT_FOUND = "/velocity/notFound.vm";
        public static final String SERVER_ERROR = "/velocity/serverError.vm";
    }

    public static class Web {
        public static final String LOGIN = "/login";
        public static final String LOGOUT = "/logout";
        public static final String PROJECTS = "/projects";
        public static final String PROJECTS_NEW = "/projects/new";
        public static final String PROJECTS_ADD = "/projects/add";
        public static final String PROJECTS_DELETE = "/projects/delete/:id";
        public static final String PROJECTS_ADD_USER = "/projects/add-user/:id";
        public static final String PROJECTS_REMOVE_USER = "/projects/remove-user/:id";
        public static final String ISSUES = "/projects/:id";
        public static final String ISSUE_NEW = "/issues/new";
        public static final String ISSUE_ONE = "/issues/one/:id";
        public static final String ISSUE_SEARCH = "/issues/search/:id";
        public static final String ISSUE_ADD = "/issues/add";
        public static final String ISSUE_UPDATE = "/issues/update/:id";
        public static final String ISSUE_DELETE = "/issues/delete/:id";
        public static final String ISSUE_COMMENT = "/issues/comment/:id";
    }
}
