package db;

import Data.Position;
import utility.SQLbuilder;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PositionsManager extends BaseManager<Position> {
    SQLbuilder builder = new SQLbuilder("positions");

    public PositionsManager() {
    }

    @Override
    protected Position mapRowToEntity(ResultSet rs) throws SQLException {
        return new Position(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("level"),
                rs.getInt("department_id"));
    }

    protected String getAddEntityQuery() {
        return "INSERT INTO positions (title, description, level, department_id) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getLoadAllQuery() {
        return this.builder.getAllData();
    }

    @Override
    protected String getLoadByIdQuery() {
        return this.builder.getSingle();
    }

    @Override
    protected String getUpdateEntityQuery() {
        return "UPDATE positions SET title = ?, description = ?, level = ?, department_id = ?, WHERE id = ?";
    }

    @Override
    protected String getDeleteEntityQuery() {
        return "DELETE FROM positions WHERE id = ?";
    }

    @Override
    protected List<Object> getUpdateFromParams(Map<String, Object> params) {
        int id = (int) ((Double) params.get("id")).doubleValue();
        String title = (String) params.get("title");
        String description = (String) params.get("description");
        String level = (String) params.get("level");
        int idDepartment = (int) ((Double) params.get("department_id")).doubleValue();

        return Arrays.asList(
                title,
                description,
                level,
                idDepartment,
                id
        );
    }
}
