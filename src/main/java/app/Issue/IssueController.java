package app.Issue;

import app.Util.Path;
import app.Util.ViewUtil;
import io.javalin.http.Handler;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static app.Application.*;
import static app.Util.RequestUtil.*;

public class IssueController
{
    public static Handler fetchAllIssues = ctx -> {
        Map<String, Object> model = ViewUtil.getBaseModel(ctx);
        model.put("issues", issueDao.getAllIssues());
        ctx.render(Path.Template.ISSUES, model);
    };

    public static Handler searchIssueByName = ctx -> {
        Map<String, Object> model = ViewUtil.getBaseModel(ctx);
        model.put("issues", issueDao.getIssuesByName(getQueryIssueSearchName(ctx)));
        ctx.render(Path.Template.ISSUES, model);
    };

    public static Handler createNewIssue = ctx -> {
        Map<String, Object> model = ViewUtil.getBaseModel(ctx);
        ctx.render(Path.Template.ISSUES_ADD, model);
    };

    public static Handler addIssue = ctx -> {
        String issueName = getQueryIssueName(ctx);
        String issueDesc = getQueryIssueDescription(ctx);

        if(StringUtils.isEmpty(issueName))
            throw new Exception("Can't add issue with empty name");

        Issue newIssue = new Issue(issueName, issueDesc);
        boolean insertResult = issueDao.insertIssue(newIssue);
        if(!insertResult)
            System.out.println("Can't add new issue: " + newIssue.toString());

        ctx.redirect(Path.Web.ISSUES);
    };

    public static Handler fetchOneIssue = ctx -> {
        Map<String, Object> model = ViewUtil.getBaseModel(ctx);

        Issue issue = issueDao.getIssuesById(getQueryIssueIdParam(ctx));
        if(issue != null)
            model.put("oneIssue", issue);

        ctx.render(Path.Template.ISSUES_UPDATE, model);
    };

    public static Handler deleteIssue = ctx -> {
        int issueId = getQueryIssueIdParam(ctx);
        boolean deleteResult = issueDao.deleteIssueById(issueId);
        if(!deleteResult)
            System.out.println("Can't delete issue by id: " + issueId);

        ctx.redirect(Path.Web.ISSUES);
    };

    public static Handler changeIssue = ctx -> {
        int issueId = getQueryIssueIdParam(ctx);
        String issueName = getQueryIssueName(ctx);
        String issueDesc = getQueryIssueDescription(ctx);

        if(StringUtils.isEmpty(issueName))
            throw new Exception("Can't add issue with empty name");

        Issue changedIssue = new Issue(issueId, issueName, issueDesc);
        boolean updateResult = issueDao.changeIssue(changedIssue);
        if(!updateResult)
            System.out.println("Can't change issue: " + changedIssue.toString());

        ctx.redirect(Path.Web.ISSUES);
    };
}
