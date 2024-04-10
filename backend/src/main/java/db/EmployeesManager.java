package db;

import Data.Employee;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EmployeesManager extends BaseManager<Employee> {
    public EmployeesManager() {
    }

    @Override
    protected Employee mapRowToEntity(ResultSet rs) throws SQLException {
        return new Employee(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getDate("date_of_birth"), rs.getString("email"), rs.getInt("department_id"), rs.getString("password"), rs.getInt("role"), rs.getString("url_image"));
    }


    protected String getAddEntityQuery() {
        return "INSERT INTO employees (first_name, last_name, date_of_birth, email, department_id, password, role, url_image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getLoadAllQuery() {
        return "SELECT * FROM employees";
    }

    @Override
    protected String getLoadAllManagerQuery() {
        return "SELECT * FROM employees WHERE department_id = ?";
    }

    @Override
    protected String getLoadByIdQuery() {
        return "SELECT * FROM employees WHERE id = ?";
    }

    @Override
    protected String getUpdateEntityQuery() {
        return "UPDATE employees SET first_name = ?, last_name = ?, date_of_birth = ?, email = ?, department_id = ?, password = ?, role = ?, url_image = ? WHERE id = ?";
    }

    @Override
    protected String getDeleteEntityQuery() {
        return "DELETE FROM employees WHERE id = ?";
    }


    @Override
    protected List<Object> getUpdateFromParams(Map<String, Object> params) {
        Integer id;
        try {
            id = (int) ((Double) params.get("id")).doubleValue();
        } catch (Exception e) {
            id = null;
        }

        String firstName = (String) params.get("first_name");
        String lastName = (String) params.get("last_name");
        Date dateOfBirth = Date.valueOf((String) params.get("date_of_birth"));
        String password = (String) params.get("password");
        String email = (String) params.get("email");
        int role = (int) ((Double) params.get("role")).doubleValue();
        int idDepartment = (int) ((Double) params.get("department_id")).doubleValue();
        String url_image = (String) params.get("url_image");

        return Arrays.asList(firstName, lastName, dateOfBirth, email, idDepartment, password, role, url_image, id);
    }
}