package db;

import Data.Employee;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EmployeesManager extends BaseManager<Employee> {
    public EmployeesManager() {
    }

    @Override
    protected Employee mapRowToEntity(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getDate("date_of_birth"),
                rs.getString("email"),
                rs.getInt("department_id"),
                rs.getString("password"),
                rs.getInt("role"));
    }


    protected String getAddEntityQuery() {
        return "INSERT INTO employees (first_name, last_name, date_of_birth, email, department_id, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
        return "UPDATE employees SET first_name = ?, last_name = ?, date_of_birth = ?, email = ?, department_id = ?, password = ?, role = ? WHERE id = ?";
    }

    @Override
    protected String getDeleteEntityQuery() {
        return "DELETE FROM employees WHERE id = ?";
    }


    @Override
    protected List<Object> getUpdateFromParams(Map<String, Object> params) {
        int id = (int) ((Double) params.get("id")).doubleValue();
        String firstName = (String) params.get("first_name");
        String lastName = (String) params.get("last_name");
        Date dateOfBirth = Date.valueOf((String) params.get("date_of_birth"));
        String password = (String) params.get("password");
        String email = (String) params.get("email");
        int role = (int) ((Double) params.get("role")).doubleValue();
        int idDepartment = (int) ((Double) params.get("department_id")).doubleValue();

        return Arrays.asList(
                firstName,
                lastName,
                dateOfBirth,
                email,
                idDepartment,
                password,
                role,
                id
        );
    }
}