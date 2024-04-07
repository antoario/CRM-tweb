package db;

import Data.Position;

import java.sql.*;
import java.util.ArrayList;

public class PositionsManager extends BaseManager<Position> {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    public PositionsManager() {}

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

    public static int editPosition(PositionsManager position) {
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("UPDATE positions SET position_title = ?, description = ?, level = ?, department_id = ? WHERE position_id = ?")) {
                st.setString(1, position.title);
                st.setString(2, position.description);
                st.setString(3, position.level);
                st.setInt(4, position.department_id);
                st.setInt(5, position.id);
                st.executeUpdate();
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return position.id;
    }
}
