package db;

import Data.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class DepartmentsManager extends BaseManager<Department> {

    public DepartmentsManager() {
    }

    @Override
    protected Department mapRowToEntity(ResultSet rs) throws SQLException {
        return null;
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
        return "SELECT * from departments";
    }

    @Override
    protected String getLoadByIdQuery() {
        return null;
    }

    @Override
    protected String getAddEntityQuery() {
        return null;
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
