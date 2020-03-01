package app.models;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.text.SimpleDateFormat;
import java.util.Date;

@Table("comments")
public class Comment extends Model {

    static {
        validatePresenceOf("description");
    }

    public Comment(){ }

    public Comment(String description, int userId, int issueId) {
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
        return getDate("created_at");
    }
    public String getCreatedDateString(){
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return df.format(getCreatedDate());
    }
    public Date getUpdatedDate()
    {
        return getDate("updated_at");
    }
    public int getUserId() {
        return getInteger("user_id");
    }
    public String getUserName(){
        User user = User.findById(getUserId());
        if(user != null){
            return user.getName();
        }
        else {
            return "Unknown";
        }
    }
    public int getIssueId()
    {
        return getInteger("issue_id");
    }
}
