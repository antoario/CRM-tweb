package db;

import Data.Benefit;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class BenefitsManager extends BaseManager<Benefit> {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    public BenefitsManager() {}

    @Override
    protected Benefit mapRowToEntity(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public int addFromParams(Map<String, String[]> params) {
        return 0;
    }

    @Override
    protected String getLoadAllQuery() {
        return "SELECT * FROM benefits";
    }

    @Override
    protected String getLoadByIdQuery() {
        return "SELECT * FROM benefits WHERE benefit_id = ?";
    }

    @Override
    protected String getAddEntityQuery() {
        return "INSERT INTO benefits (description, value, employee_id)";
    }

    @Override
    protected String getUpdateEntityQuery() {
        return "UPDATE benefits SET description = ?, value = ?, employee_id = ? WHERE benefit_id = ?\"";
    }

    @Override
    protected String getDeleteEntityQuery() {
        return "";
    }
}
