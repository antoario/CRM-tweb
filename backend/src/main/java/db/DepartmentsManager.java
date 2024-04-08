package db;

import Data.Department;
import utility.SQLbuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DepartmentsManager extends BaseManager<Department> {

    SQLbuilder builder = new SQLbuilder("departments");

    public DepartmentsManager() {

    }

    @Override
    protected Department mapRowToEntity(ResultSet rs) throws SQLException {
        return new Department(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("manager"));
    }

    @Override
    public int addFromParams(Map<String, Object> params) {
        String name = (String) params.get("name");
        String description = (String) params.get("description");
        int manager = (int) ((Double) params.get("manager")).doubleValue();

        List<Object> values = Arrays.asList(
                name,
                description,
                manager
        );

        return addEntity(values);
    }

    @Override
    public int updateFromParams(Map<String, Object> params) {
        int id = (int) ((Double) params.get("id")).doubleValue();
        String name = (String) params.get("name");
        String description = (String) params.get("description");
        int manager = (int) ((Double) params.get("manager")).doubleValue();

        List<Object> values = Arrays.asList(
                name,
                description,
                manager,
                id
        );

        return updateEntity(values);
    }

    protected String getAddEntityQuery() {
        return "INSERT INTO departments (name, description, manager) VALUES (?, ?, ?)";
    }

    @Override
    protected String getLoadAllQuery() {
        return builder.getAllData();
    }

    @Override
    protected String getLoadByIdQuery() {
        return builder.getSingle();
    }

    @Override
    protected String getUpdateEntityQuery() {
        return "UPDATE departments SET name = ?, description = ?, manager = ? WHERE id = ?";
    }

    @Override
    protected String getDeleteEntityQuery() {
        return "DELETE * FROM departments WHERE id = ?";
    }
}
