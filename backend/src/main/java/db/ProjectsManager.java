package db;

import java.sql.*;
import java.util.ArrayList;

public class ProjectsManager {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    private final int id;
    private final String name;
    private final String description;
    private final Date start_date;
    private final Date end_date;
    private final int department_id;

    public ProjectsManager(int id, String name, String description, Date start_date, Date end_date, int department_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.department_id = department_id;
    }

    public static ArrayList<ProjectsManager> loadAllProjects() {
        ArrayList<ProjectsManager> allProjects = new ArrayList<>();
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM projects")) {
                ResultSet rs = st.executeQuery();
                while(rs.next()) {
                    ProjectsManager temp = new ProjectsManager(rs.getInt("project_id"),
                            rs.getString("project_name"),
                            rs.getString("description"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date"),
                            rs.getInt("department_id"));
                    allProjects.add(temp);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return allProjects;
    }

    public static ProjectsManager loadProjectDetails(int id) {
        ProjectsManager project = null;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM projects WHERE project_id = ?")) {
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    project = new ProjectsManager(rs.getInt("project_id"),
                            rs.getString("project_name"),
                            rs.getString("description"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date"),
                            rs.getInt("department_id"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return project;
    }

    public static int addProject(ProjectsManager project) {
        int generatedId = -2;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO projects (project_name, description, start_date, end_date, department_id)" +
                    " VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                st.setString(1, project.name);
                st.setString(2, project.description);
                st.setDate(3, project.start_date);
                st.setDate(4, project.end_date);
                st.setInt(5, project.department_id);
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
