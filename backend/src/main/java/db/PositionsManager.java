package db;

import Data.Employee;
import Data.Position;
import utility.SQLbuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PositionsManager extends BaseManager<Position> {
    public PositionsManager() {}

    SQLbuilder builder = new SQLbuilder("positions");

    @Override
    protected Position mapRowToEntity(ResultSet rs) throws SQLException {
        return new Position(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("level"),
                rs.getInt("department_id"));
    }

    @Override
    public int addFromParams(Map<String, Object> params) {
        String title = (String) params.get("title");
        String description = (String) params.get("description");
        String level = (String) params.get("level");
        int idDepartment = (int) ((Double) params.get("department_id")).doubleValue();

        List<Object> values = Arrays.asList(
                title,
                description,
                level,
                idDepartment
        );

        return addEntity(values);
    }

    @Override
    public int updateFromParams(Map<String, Object> params) {
        int id = (int) ((Double) params.get("id")).doubleValue();
        String title = (String) params.get("title");
        String description = (String) params.get("description");
        String level = (String) params.get("level");
        int idDepartment = (int) ((Double) params.get("department_id")).doubleValue();

        List<Object> values = Arrays.asList(
                title,
                description,
                level,
                idDepartment,
                id
        );

        return updateEntity(values);
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
        return "DELETE * FROM positions WHERE id = ?";
    }
}
