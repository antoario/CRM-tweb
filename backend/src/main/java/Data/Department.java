package Data;

public class Department {
    private int id;
    private String name;
    private String description;
    private String manager;

    public Department(int id, String name, String description, String manager) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.manager = manager;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
