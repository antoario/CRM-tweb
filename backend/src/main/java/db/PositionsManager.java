package db;

import Data.Position;
import utility.SQLbuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class PositionsManager extends BaseManager<Position> {

    SQLbuilder builder = new SQLbuilder("positions");

    @Override
    protected Position mapRowToEntity(ResultSet rs) throws SQLException {
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
        return this.builder.getAllData();
    }

    @Override
    protected String getLoadByIdQuery() {
        return this.builder.getSingle();
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
