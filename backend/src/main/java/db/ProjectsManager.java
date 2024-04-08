package db;

import Data.Project;

import java.sql.*;
import java.util.Map;

public class ProjectsManager extends BaseManager<Project> {

    @Override
    protected Project mapRowToEntity(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public String addFromParams(Map<String, Object> params) {
        return 0;
    }

    @Override
    public int updateFromParams(Map<String, Object> params) {
        return 0;
    }

    @Override
    protected String getLoadAllQuery() {
        return null;
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
