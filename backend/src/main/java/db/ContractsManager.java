package db;

import Data.Contract;
import utility.SQLbuilder;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ContractsManager extends BaseManager<Contract> {
    SQLbuilder builder = new SQLbuilder("contracts");

    public ContractsManager() {
    }

    @Override
    protected Contract mapRowToEntity(ResultSet rs) throws SQLException {
        return new Contract(
                rs.getInt("id"),
                rs.getInt("employee_id"),
                rs.getString("contract_type"),
                rs.getDate("start_date"),
                rs.getDate("end_date"),
                rs.getFloat("salary"));
    }

    protected String getAddEntityQuery() {
        return "INSERT INTO contracts (employee_id, contract_type, start_date, end_date, salary) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getLoadAllQuery() {
        return "SELECT * FROM contracts";
    }

    @Override
    protected String getLoadByIdQuery() {
        return this.builder.getAllData();
    }

    @Override
    protected String getUpdateEntityQuery() {
        return "UPDATE contracts SET employee_id = ?, contract_type = ?, start_date = ?, end_date = ?, salary = ? WHERE id = ?";
    }

    @Override
    protected String getDeleteEntityQuery() {
        return this.builder.getSingle();
    }

    @Override
    protected List<Object> getUpdateFromParams(Map<String, Object> params) {
        int id = (int) ((Double) params.get("id")).doubleValue();
        int employeeId = (int) ((Double) params.get("employee_id")).doubleValue();
        String contractType = (String) params.get("contract_type");
        Date startDate = (Date) params.get("start_date");
        Date endDate = (Date) params.get("end_date");
        float salary = (float) params.get("salary");

        return Arrays.asList(
                employeeId,
                contractType,
                startDate,
                endDate,
                salary,
                id
        );
    }


    private int updateContract() {
        return 0;
    }

    private boolean deleteContract() {
        return false;
    }
}
