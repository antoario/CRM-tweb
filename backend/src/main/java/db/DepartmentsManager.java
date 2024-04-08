package db;

import Data.Department;
import utility.SQLbuilder;

import java.sql.*;
import java.util.ArrayList;
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
                rs.getInt("id_manager")
        );
    }

    @Override
    public int addFromParams(Map<String, Object> params) {
        return 0;
    }

    @Override
    public int updateFromParams(Map<String, Object> params) {
        return 0;
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
    protected String getAddEntityQuery() {
        return "DELETE * FROM departments WHERE id = ? VALUES (?)";
    }

    @Override
    protected String getUpdateEntityQuery() {
        return null;
    }

    @Override
    protected String getDeleteEntityQuery() {
        return null;
    }
}
