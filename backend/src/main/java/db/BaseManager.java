package db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utility.ErrorHandler;
import utility.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseManager<T> {
    protected final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    protected abstract T mapRowToEntity(ResultSet rs) throws SQLException;

    protected abstract String getLoadAllQuery();

    protected abstract String getLoadAllManagerQuery();

    protected abstract String getLoadByIdQuery();

    protected abstract String getAddEntityQuery();

    protected abstract String getUpdateEntityQuery();

    protected abstract String getDeleteEntityQuery();

    protected abstract List<Object> getUpdateFromParams(Map<String, Object> params);

    public String loadAll() {
        ArrayList<T> entities = new ArrayList<>();
        String query = getLoadAllQuery();
        try (Connection conn = persistence.getConnection();
             PreparedStatement st = conn.prepareStatement(query)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                entities.add(mapRowToEntity(rs));
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd") // Usa il formato di data che preferisci
                .create();
        return gson.toJson(entities);
    }

    public String addFromParams(Map<String, Object> params) {
        try {
            Response res = addEntity(getUpdateFromParams(params));
            return new Gson().toJson(res);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Gson().toJson(new Response(-1, ex.getMessage()));
        }
    }

    public String updateFromParams(Map<String, Object> params) throws SQLException {
        record Data(int id, String message) {
        }
        Response res = new Response(0, new Data(updateEntity(getUpdateFromParams(params)), "Update successful!"));
        return new Gson().toJson(res);
    }

    public T loadById(int id) {
        T entity = null;
        String query = getLoadByIdQuery();
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

    public String loadManagerView(int department_id) {
        ArrayList<T> entities = new ArrayList<>();
        String query = getLoadAllManagerQuery();
        try (Connection conn = persistence.getConnection();
             PreparedStatement st = conn.prepareStatement(query)) {
            st.setInt(1, department_id);
            System.out.println(st);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                entities.add(mapRowToEntity(rs));
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return new Gson().toJson(entities);
    }

    protected Response addEntity(List<Object> values) {
        int generatedId;

        try (Connection conn = persistence.getConnection();
             PreparedStatement st = conn.prepareStatement(getAddEntityQuery(), Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < values.size() - 1; i++) {
                if (values.get(i) == "id") continue;
                st.setObject(i + 1, values.get(i));
            }

            int affectedRows = st.executeUpdate();
            if (affectedRows == 0) throw new SQLException("Creating entity failed, no rows affected.");

            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) generatedId = generatedKeys.getInt(1);
                else throw new SQLException("Creating entity failed, no ID obtained.");
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
            return new Response(-1, new ResponseDataFail(ex.getMessage()));
        }
        return new Response(0, new ResponseData(generatedId, "Success"));
    }

    public int updateEntity(List<Object> values) throws SQLException {
        doQuery(getUpdateEntityQuery(), values);
        return (int) values.get(values.size() - 1);
    }

    private void doQuery(String query, List<Object> values) throws SQLException {
        Connection conn = persistence.getConnection();
        PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < values.size(); i++) st.setObject(i + 1, values.get(i));

        System.out.println("Numero di righe aggiornate: " + st.executeUpdate());
    }

    public boolean deleteEntity(int id) throws SQLException {
        String query = getDeleteEntityQuery();
        try (Connection conn = persistence.getConnection();
             PreparedStatement st = conn.prepareStatement(query)) {
            st.setInt(1, id);
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        }
    }

    record ResponseData(int id, String message) {
    }

    record ResponseDataFail(String message) {
    }
}
