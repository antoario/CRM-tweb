package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeesManager {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    private final int id;
    private final String first_name;
    private final String last_name;
    private final String date_of_birth;
    private final String email;
    private final int department_id;

    public EmployeesManager(int id, String first_name, String last_name, String date_of_birth, String email, int department_id) {
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
                            rs.getString("date_of_birth"),
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
                            rs.getString("date_of_birth"),
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
                            rs.getString("date_of_birth"),
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
}
