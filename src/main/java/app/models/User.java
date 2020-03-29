package app.models;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.validation.UniquenessValidator;
import org.javalite.activejdbc.annotations.Table;

@Table("users")
public class User extends Model {

    static {
        validatePresenceOf("name", "password");
        validateEmailOf("email");
        validateWith(new UniquenessValidator("email")).message("This email is already taken.");
    }

    public User(){ }

    public User(String name, String email, String password) {
        set("name", name,
            "email", email,
            "password", password);
    }

    public String getName()
    {
        return getString("name");
    }
}
