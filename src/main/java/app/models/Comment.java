package app.models;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.util.Date;

@Table("comments")
public class Comment extends Model {

    static {
        validatePresenceOf("description");
        dateFormat("yyyy-MM-dd HH:mm:ss", "created_at");
        dateFormat("yyyy-MM-dd HH:mm:ss", "updated_at");
    }

    public Comment(){ }

    public Comment(String description, Date created, int userId, int issueId) {
        set("description", description,
            "user_id", userId,
            "issue_id", issueId
        );
    }

    public String getDescription()
    {
        return getString("description");
    }
    public Date getCreatedDate()
    {
        return  getDate("created_at");
    }
    public Date getUpdatedDate()
    {
        return  getDate("updated_at");
    }
    public int getUserId()
    {
        return getInteger("user_id");
    }
    public int getIssueId()
    {
        return getInteger("issue_id");
    }
}
