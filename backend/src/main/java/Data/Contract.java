package Data;

import java.sql.Date;

public class Contract {

    private int id;
    private int employeeId;
    private String contractType;
    private Date startDate;
    private Date endDate;
    private float salary;

    public Contract(int id, int employeeId, String contractType, Date startDate, Date endDate, float salary) {
        this.id = id;
        this.employeeId = employeeId;
        this.contractType = contractType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

}
