package app.Issue;

import app.DB.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IssueDao
{
    private final String selectIssuesQuery = "Select * From issue";
    private final String selectIssuesByNameQueryFormat = "Select * From issue Where Instr(name, '%s') = 1";
    // TODO: provide checking query on presence of single quote, and if it's true - adding one more quote to that
    private final String selectIssueByIdQueryFormat = "Select * From issue Where id = %d";
    private final String insertIssueQueryFormat = "Insert Into issue(name, description) Values('%s', '%s')";
    private final String deleteIssueQueryFormat = "Delete From issue Where id = %d";
    private final String updateIssueQueryFormat = "Update issue Set name = '%s', description = '%s' Where id = %d";

    public IssueDao()
    {
    }

    public Issue getIssuesById(int id)
    {
        try
        {
            String query = String.format(selectIssueByIdQueryFormat, id);
            ResultSet res = DBConnector.ExecuteSelectQuery(query);

            res.next();
            Issue issue = new Issue(
                    res.getInt("id"),
                    res.getString("name"),
                    res.getString("description")
            );

            return issue;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<Issue> getAllIssues()
    {
        List<Issue> data = new ArrayList<Issue>();

        try
        {
            ResultSet res = DBConnector.ExecuteSelectQuery(selectIssuesQuery);
            while (res.next())
            {
                Issue issue = new Issue(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getString("description")
                );
                data.add(issue);
            }
            return data;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            return data;
        }
    }

    public List<Issue> getIssuesByName(String subName)
    {
        List<Issue> data = new ArrayList<Issue>();

        try
        {
            String query = String.format(selectIssuesByNameQueryFormat, subName);
            ResultSet res = DBConnector.ExecuteSelectQuery(query);
            while (res.next())
            {
                Issue issue = new Issue(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getString("description")
                );
                data.add(issue);
            }
            return data;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            return data;
        }
    }

    public boolean insertIssue(Issue issue)
    {
        try
        {
            String query = String.format(insertIssueQueryFormat, issue.getName(), issue.getDescription());
            return DBConnector.ExecuteQuery(query);
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean deleteIssueById(int id)
    {
        try
        {
            String query = String.format(deleteIssueQueryFormat, id);
            return DBConnector.ExecuteQuery(query);
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean changeIssue(Issue issue)
    {
        try
        {
            String query = String.format(updateIssueQueryFormat, issue.getName(), issue.getDescription(), issue.getId());
            return DBConnector.ExecuteQuery(query);
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
