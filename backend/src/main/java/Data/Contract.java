package Data;

import java.sql.Date;

public class Contract {

    private int id;
    private int employee_id;
    private String contract_type;
    private Date start_date;
    private Date end_date;
    private float salary;

    public Contract(int id, int employee_id, String contract_type, Date start_date, Date end_date, float salary) {
        this.id = id;
        this.employee_id = employee_id;
        this.contract_type = contract_type;
        this.start_date = start_date;
        this.end_date = end_date;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getContract_type() {
        return contract_type;
    }

    public void setContract_type(String contract_type) {
        this.contract_type = contract_type;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
}
