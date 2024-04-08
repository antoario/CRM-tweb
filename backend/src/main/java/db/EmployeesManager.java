package db;

import Data.Employee;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EmployeesManager extends BaseManager<Employee> {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

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

    @Override
    public int addFromParams(Map<String, Object> params) {
        String firstName = (String) params.get("first_name");
        String lastName = (String) params.get("last_name");
        Date dateOfBirth = Date.valueOf((String) params.get("date_of_birth"));
        String password = (String) params.get("password");
        String email = (String) params.get("email");
        int role = (int) ((Double) params.get("role")).doubleValue();
        int idDepartment = (int) ((Double) params.get("department_id")).doubleValue();

        Employee employee = new Employee(-1, firstName, lastName, dateOfBirth, email, idDepartment, password, role);

        List<Object> values = Arrays.asList(
                employee.getFirst_name(),
                employee.getLast_name(),
                employee.getDate_of_birth(),
                employee.getPassword(),
                employee.getEmail(),
                employee.getRole(),
                employee.getId_departments()
        );

        return addEntity(values);
    }

    @Override
    public int updateFromParams(Map<String, Object> params) {
        int id = (int) ((Double) params.get("id")).doubleValue();
        String firstName = (String) params.get("first_name");
        String lastName = (String) params.get("last_name");
        System.out.println(params.get("date_of_birth"));
        Date dateOfBirth = Date.valueOf((String) params.get("date_of_birth"));
        String password = (String) params.get("password");
        String email = (String) params.get("email");
        int role = (int) ((Double) params.get("role")).doubleValue();
        int idDepartment = (int) ((Double) params.get("id_departments")).doubleValue();

        Employee employee = new Employee(-1, firstName, lastName, dateOfBirth, email, idDepartment, password, role);

        List<Object> values = Arrays.asList(
                employee.getId(),
                employee.getFirst_name(),
                employee.getLast_name(),
                employee.getDate_of_birth(),
                employee.getPassword(),
                employee.getEmail(),
                employee.getRole(),
                employee.getId_departments()
        );

        return updateEntity(values);
    }

    protected String getAddEntityQuery() {
        return "INSERT INTO employees (first_name, last_name, date_of_birth, password, email, role, id_departments) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getLoadAllQuery() {
        return "SELECT * FROM employees";
    }

    @Override
    protected String getLoadByIdQuery() {
        return "SELECT * FROM employees WHERE id = ?";
    }

    @Override
    protected String getUpdateEntityQuery() {
        return "UPDATE employees SET first_name = ?, last_name = ?, date_of_birth = ?, email = ?, department_id = ? WHERE id = ?";
    }

    @Override
    protected String getDeleteEntityQuery() {
        return "DELETE * FROM employees WHERE id = ?";
    }


}