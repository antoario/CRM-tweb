package db;

import Data.Benefit;
import utility.SQLbuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class BenefitsManager extends BaseManager<Benefit> {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();
    SQLbuilder builder = new SQLbuilder("benefits");

    public BenefitsManager() {
    }

    @Override
    protected Benefit mapRowToEntity(ResultSet rs) throws SQLException {
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
        return "INSERT INTO benefits (description, value, employee_id)";
    }

    @Override
    protected String getUpdateEntityQuery() {
        return "UPDATE benefits SET description = ?, value = ?, employee_id = ? WHERE id = ?";
    }

    @Override
    protected String getDeleteEntityQuery() {
        return "";
    }
}
