package Data;

public class Benefit {
    private int id;
    private String description;
    private String value;
    private int employee_id;

    public Benefit(int id, String description, String value, int employee_id) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.employee_id = employee_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
}
