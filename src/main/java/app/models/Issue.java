package app.models;
import app.util.enums.*;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.util.Date;

@Table("issues")
public class Issue extends Model {

    static {
        validatePresenceOf("name");
    }

    public Issue(){ }

    public Issue(String name, String description, CriticalType criticalType, PriorityType priorityType,
                 String steps, String expected, String actual, StatusType statusType, int authorId, int projectId)
    {
        set("name", name,
            "description", description,
            "critical", criticalType.getValue(),
            "priority", priorityType.getValue(),
            "steps", steps,
            "expected", expected,
            "actual", actual,
            "status", statusType.getValue(),
            "user_id", authorId,
            "project_id", projectId);
    }

    public String getName(){return getString("name");}
    public String getDescription(){return getString("description");}
    public CriticalType getCritical(){
        return CriticalType.getEnum(getInteger("critical"));
    }
    public String getCriticalStringNumber(){
        return String.valueOf(getInteger("critical"));
    }
    public PriorityType getPriority(){
        return PriorityType.getEnum(getInteger("priority"));
    }
    public String getPriorityStringNumber(){
        return String.valueOf(getInteger("priority"));
    }
    public String getSteps(){return getString("steps");}
    public String getExpected(){return getString("expected");}
    public String getActual(){return getString("actual");}
    public int getExecutorId(){
        try{ return getInteger("executor_id"); }
        catch (Exception ex) { return 0; }
    }
    public StatusType getStatus(){
        return StatusType.getEnum(getInteger("status"));
    }
    public String getStatusStringNumber(){
        return String.valueOf(getInteger("status"));
    }
    public Date getCreatedDate(){
        return getDate("created_at");
    }
    public Date getUpdatedDate(){
        return getDate("updated_at");
    }
    public int getAuthorId(){
        return getInteger("user_id");
    }
    public int getProjectId() { return getInteger("project_id"); }
}
