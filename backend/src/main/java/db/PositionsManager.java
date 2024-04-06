package db;

import java.sql.*;
import java.util.ArrayList;

public class PositionsManager {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    private final int id;
    private final String title;
    private final String description;
    private final String level;
    private final int department_id;

    public PositionsManager(int id, String title, String description, String level, int department_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.level = level;
        this.department_id = department_id;
    }

    public static ArrayList<PositionsManager> loadAllPositions() {
        ArrayList<PositionsManager> allPositions = new ArrayList<>();
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM positions")) {
                ResultSet rs = st.executeQuery();
                while(rs.next()) {
                    PositionsManager temp = new PositionsManager(rs.getInt("position_id"),
                            rs.getString("position_title"),
                            rs.getString("description"),
                            rs.getString("level"),
                            rs.getInt("department_id"));
                    allPositions.add(temp);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return allPositions;
    }

    public static PositionsManager loadPositionDetails(int id) {
        PositionsManager position = null;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM positions WHERE position_id = ?")) {
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    position = new PositionsManager(rs.getInt("position_id"),
                            rs.getString("position_title"),
                            rs.getString("description"),
                            rs.getString("level"),
                            rs.getInt("department_id"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return position;
    }

    public static int addPosition(PositionsManager position) {
        int generatedId = -2;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO positions (position_title, description, level, department_id)" +
                    " VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                st.setString(1, position.title);
                st.setString(2, position.description);
                st.setString(3, position.level);
                st.setInt(4, position.department_id);
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
