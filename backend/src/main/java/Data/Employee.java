package Data;

import java.util.Date;

public class Employee {

    int id;
    String first_name;
    String last_name;
    Date date_of_birth;
    String password;
    String email;
    int role;
    int id_departments;

    public Employee(int id, String first_name, String last_name, Date date_of_birth, String password, String email, int role, int id_departments) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.password = password;
        this.email = email;
        this.role = role;
        this.id_departments = id_departments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_departments() {
        return id_departments;
    }

    public void setId_departments(int id_departments) {
        this.id_departments = id_departments;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }


}
