package app.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnector
{
    private static Connection connection;
    private static Statement statement;

    private static final String dbUrl = "jdbc:sqlite:C:\\My_Documents\\Repos\\IssueTracker_TSS\\trellolike\\src\\main\\" +
            "resources\\issues.db";

    public static void Connection() throws SQLException
    {
        if(connection == null)
            connection = DriverManager.getConnection(dbUrl);
        if(statement == null)
            statement = connection.createStatement();
    }

    public static void Close() throws SQLException
    {
        if(statement != null)
            statement.close();
        if(connection != null)
            connection.close();
    }

    public static ResultSet ExecuteSelectQuery(String query) throws SQLException
    {
        return statement.executeQuery(query);
    }

    public static boolean ExecuteQuery(String query) throws SQLException
    {
        return !statement.execute(query);
    }
}
