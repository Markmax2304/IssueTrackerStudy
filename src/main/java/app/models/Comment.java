package app.models;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("comments")
public class Comment extends Model {

    static {
        validatePresenceOf("description");
    }

    public Comment(){ }

    public Comment(String description) {
        set("description", description);
    }
}
