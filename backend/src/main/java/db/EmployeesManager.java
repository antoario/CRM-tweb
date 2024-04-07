package db;

import Data.Employee;

import java.sql.*;
import java.util.ArrayList;

public class EmployeesManager extends BaseManager<Employee> {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    public EmployeesManager() {}

    public static ArrayList<EmployeesManager> loadAllEmployees() {
        ArrayList<EmployeesManager> allEmployees = new ArrayList<>();
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM employees")) {
                ResultSet rs = st.executeQuery();
                while(rs.next()) {
                    EmployeesManager temp = new EmployeesManager(rs.getInt("employee_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getDate("date_of_birth"),
                            rs.getString("email"),
                            rs.getInt("department_id"));
                    allEmployees.add(temp);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return allEmployees;
    }

    public static EmployeesManager loadEmployeeDetails(int id) {
        EmployeesManager employee = null;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM employees WHERE employee_id = ?")) {
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    employee = new EmployeesManager(rs.getInt("employee_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getDate("date_of_birth"),
                            rs.getString("email"),
                            rs.getInt("department_id"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return employee;
    }

    public static int addEmployee(EmployeesManager employee) {
        int generatedId = -2;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO employees (first_name, last_name, date_of_birth, email, department_id)" +
                    " VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                st.setString(1, employee.first_name);
                st.setString(2, employee.last_name);
                st.setDate(3, employee.date_of_birth);
                st.setString(4, employee.email);
                st.setInt(5, employee.department_id);
                st.executeUpdate();

                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return generatedId;
    }

    public static int editEmployee(EmployeesManager employee) {
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("UPDATE employees SET first_name = ?, last_name = ?, date_of_birth = ?, email = ?, department_id = ? WHERE employee_id = ?")) {
                st.setString(1, employee.first_name);
                st.setString(2, employee.last_name);
                st.setDate(3, employee.date_of_birth);
                st.setString(4, employee.email);
                st.setInt(5, employee.department_id);
                st.setInt(6, employee.id);
                st.executeUpdate();
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return employee.id;
    }
}