package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseManager<T> implements GenericManager<T> {
    protected final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    protected abstract T mapRowToEntity(ResultSet rs) throws SQLException;

    public abstract int addFromParams(Map<String, String[]> params);

    public List<T> loadAll() {
        List<T> entities = new ArrayList<>();
        String query =  getLoadAllQuery();
        try (Connection conn = persistence.getConnection();
             PreparedStatement st = conn.prepareStatement(query)) {
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                entities.add(mapRowToEntity(rs));
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return entities;
    }

    protected T loadById(String query, int id) {
        T entity = null;
        try (Connection conn = persistence.getConnection();
             PreparedStatement st = conn.prepareStatement(query)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    entity = mapRowToEntity(rs);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return entity;
    }

    protected int addEntity(String query, List<Object> values) {
        int generatedId = -1; // O un altro valore di default che indica il fallimento
        try (Connection conn = persistence.getConnection();
             PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Imposta i valori nella PreparedStatement
            for (int i = 0; i < values.size(); i++) {
                st.setObject(i + 1, values.get(i));
            }

            int affectedRows = st.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating entity failed, no rows affected.");
            }

            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating entity failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return generatedId;
    }

    protected abstract String getLoadAllQuery();


    // Implementazioni di add, edit e loadDetails seguono un approccio simile
}
