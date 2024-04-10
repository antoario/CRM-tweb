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
                rs.getString("description"),
                rs.getInt("manager_id"));
    }

    protected String getAddEntityQuery() {
        return "INSERT INTO departments (name, description, manager) VALUES (?, ?, ?)";
    }

    @Override
    protected String getLoadAllQuery() {
        return builder.getAllData();
    }

    @Override
    protected String getLoadAllManagerQuery() {
        return "SELECT * FROM employees WHERE department_id = ?";
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
        return "DELETE FROM departments WHERE id = ?";
    }

    @Override
    protected List<Object> getUpdateFromParams(Map<String, Object> params) {
        int id = (int) ((Double) params.get("id")).doubleValue();
        String name = (String) params.get("name");
        String description = (String) params.get("description");
        int manager = (int) ((Double) params.get("manager")).doubleValue();

        return Arrays.asList(
                name,
                description,
                manager,
                id
        );
    }
}
