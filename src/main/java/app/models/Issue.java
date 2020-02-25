package app.models;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("issues")
public class Issue extends Model {

    static {
        validatePresenceOf("name");
    }

    public Issue(){ }

    public Issue(String name, String description) {
        set("name", name, "description", description);
    }
}
