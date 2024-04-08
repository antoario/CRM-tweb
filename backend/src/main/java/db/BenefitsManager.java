package db;

import Data.Benefit;
import utility.SQLbuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BenefitsManager extends BaseManager<Benefit> {
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

    @Override
    protected List<Object> getUpdateFromParams(Map<String, Object> params) {
        int id = (int) ((Double) params.get("id")).doubleValue();
        String description = (String) params.get("description");
        String value = (String) params.get("value");
        int employeeId = (int) ((Double) params.get("employee_id")).doubleValue();

        return Arrays.asList(
                description,
                value,
                employeeId,
                id
        );
    }
}
