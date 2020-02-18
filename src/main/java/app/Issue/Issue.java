package app.Issue;

public class Issue
{
    private int id;
    private String name;
    private String description;

    public Issue(int id, String name, String description)
    {
        this(name, description);
        this.id = id;
    }

    public Issue(String name, String description)
    {
        this.id = -1;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString()
    {
        return String.format("%d, %s, %s", id, name, description);
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public void setName(String value) {name = value;}
    public String getDescription() {return description;}
    public void setDescription(String value) {description = value;}
}
