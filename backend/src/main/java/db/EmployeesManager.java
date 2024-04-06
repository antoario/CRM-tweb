package db;

import java.sql.*;
import java.util.ArrayList;

public class EmployeesManager {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    private final int id;
    private final String first_name;
    private final String last_name;
    private final Date date_of_birth;
    private final String email;
    private final int department_id;

    public EmployeesManager(int id, String first_name, String last_name, Date date_of_birth, String email, int department_id) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.email = email;
        this.department_id = department_id;
    }

        public static EmployeesManager validateCredentials(String email, String password) {
        EmployeesManager employee = null;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM employees WHERE email = ?")) {
                st.setString(1, email);
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
}