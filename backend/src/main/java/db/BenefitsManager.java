package db;

import Data.Benefit;
import utility.SQLbuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BenefitsManager extends BaseManager<Benefit> {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();
    SQLbuilder builder = new SQLbuilder("benefits");

    public BenefitsManager() {
    }

    @Override
    protected Benefit mapRowToEntity(ResultSet rs) throws SQLException {
        return new Benefit(
                rs.getInt("id"),
                rs.getString("description"),
                rs.getString("value"),
                rs.getInt("employee_id"));
    }

    @Override
    public int addFromParams(Map<String, Object> params) {
        String description = (String) params.get("description");
        String value = (String) params.get("value");
        int employeeId = (int) ((Double) params.get("employee_id")).doubleValue();

        List<Object> values = Arrays.asList(
                description,
                value,
                employeeId
        );

        return addEntity(values);
    }

    @Override
    public int updateFromParams(Map<String, Object> params) {
        int id = (int) ((Double) params.get("id")).doubleValue();
        String description = (String) params.get("description");
        String value = (String) params.get("value");
        int employeeId = (int) ((Double) params.get("employee_id")).doubleValue();

        List<Object> values = Arrays.asList(
                description,
                value,
                employeeId,
                id
        );

        return updateEntity(values);
    }

    protected String getAddEntityQuery() {
        return "INSERT INTO benefits (description, value, employee_id) VALUES (?, ?, ?)";
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
    protected String getUpdateEntityQuery() {
        return "UPDATE benefits SET description = ?, value = ?, employee_id = ? WHERE id = ?";
    }

    @Override
    protected String getDeleteEntityQuery() {
        return "DELETE * FROM benefits WHERE id = ?";
    }
}
