package db;

import Data.Department;
import utility.SQLbuilder;

import java.sql.*;
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
                rs.getString("description"));
    }

    protected String getAddEntityQuery() {
        return "INSERT INTO departments (name, description) VALUES (?, ?)";
    }

    @Override
    protected String getLoadAllQuery() {
        return builder.getAllData();
    }

    @Override
    protected String getLoadAllManagerQuery() {
        return "SELECT * FROM departments WHERE department_id = ?";
    }

    @Override
    protected String getLoadByIdQuery() {
        return builder.getSingle();
    }

    @Override
    protected String getUpdateEntityQuery() {
        return "UPDATE departments SET name = ?, description = ?, manager_id = ? WHERE id = ?";
    }

    @Override
    protected String getDeleteEntityQuery() {
        return "DELETE FROM departments WHERE id = ?";
    }

    @Override
    protected List<Object> getUpdateFromParams(Map<String, Object> params) {
        Integer id;
        try {
            id = (int) ((Double) params.get("id")).doubleValue();
        } catch (Exception ex) {
            id = null;
        }
        String name = (String) params.get("name");
        String description = (String) params.get("description");

        return Arrays.asList(
                name,
                description,
                id
        );
    }
}
