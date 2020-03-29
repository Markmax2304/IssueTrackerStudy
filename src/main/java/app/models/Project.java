package app.models;

import app.util.enums.AccessType;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("projects")
public class Project extends Model
{
    static {
        validatePresenceOf("name");
    }

    public Project(){ }

    public Project(String name, int userId, AccessType access){
        set("name", name,
            "creator_id", userId,
            "access", access.getValue()
        );
    }

    public String getName()
    {
        return getString("name");
    }
    public String getAccessString(){
        return AccessType.getEnum(getInteger("access")).toString();
    }
    public int getCreatorId() { return getInteger("creator_id"); }
}
