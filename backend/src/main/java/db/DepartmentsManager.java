package db;

import Data.Department;

import java.sql.*;
import java.util.ArrayList;

public class DepartmentsManager extends BaseManager<Department> {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    public DepartmentsManager() {}

    public static ArrayList<DepartmentsManager> loadAllDepartments() {
        ArrayList<DepartmentsManager> allDepartments = new ArrayList<>();
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM departments")) {
                ResultSet rs = st.executeQuery();
                while(rs.next()) {
                    DepartmentsManager temp = new DepartmentsManager(rs.getInt("department_id"),
                            rs.getString("department_name"),
                            rs.getString("description"),
                            rs.getString("manager"));
                    allDepartments.add(temp);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return allDepartments;
    }

    public static DepartmentsManager loadDepartmentDetails(int id) {
        DepartmentsManager department = null;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM departments WHERE department_id = ?")) {
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    department = new DepartmentsManager(rs.getInt("department_id"),
                            rs.getString("department_name"),
                            rs.getString("description"),
                            rs.getString("manager"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return department;
    }

    public static int addDepartment(DepartmentsManager department) {
        int generatedId = -2;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO departments (department_name, description, manager)" +
                    " VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                st.setString(1, department.name);
                st.setString(2, department.description);
                st.setString(3, department.manager);
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

    public static int editDepartment(DepartmentsManager department) {
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("UPDATE departments SET department_name = ?, description = ?, manager = ? WHERE department_id = ?")) {
                st.setString(1, department.name);
                st.setString(2, department.description);
                st.setString(3, department.manager);
                st.setInt(4, department.id);
                st.executeUpdate();
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return department.id;
    }
}
