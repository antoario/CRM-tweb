package db;

import Data.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class ProjectsManager extends BaseManager<Project> {

    @Override
    protected Project mapRowToEntity(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public int addFromParams(Map<String, Object> params) {
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
