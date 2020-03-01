package app.util;

public class Path
{
    public static class Template {
        /*public static final String INDEX = "/velocity/index/index.vm";
        public static final String LOGIN = "/velocity/login/login.vm";*/
        public static final String ISSUES = "/velocity/issue/issues.vm";
        public static final String ISSUES_ADD = "/velocity/issue/add-issue.vm";
        public static final String ISSUES_UPDATE = "/velocity/issue/one-issue.vm";
        public static final String NOT_FOUND = "/velocity/notFound.vm";
    }

    public static class Web {
        /*public static final String INDEX = "/index";
        public static final String LOGIN = "/login";
        public static final String LOGOUT = "/logout";*/
        public static final String ISSUES = "/issues";
        public static final String ISSUE_SEARCH = "/issues/search";
        public static final String ISSUE_ADD = "/issues/add";
        public static final String ISSUE_UPDATE = "/issues/update/:id";
        public static final String ISSUE_DELETE = "/issues/update/delete/:id";
        public static final String ISSUE_CHANGE = "/issues/update/change/:id";
    }
}
