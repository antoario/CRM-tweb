package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepartmentsManager {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    private final int id;
    private final String name;
    private final String description;
    private final String manager;

    public DepartmentsManager(int id, String name, String description, String manager) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.manager = manager;
    }

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
}
