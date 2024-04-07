package db;

import Data.Contract;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ContractsManager extends BaseManager<Contract> {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    public ContractsManager() {}

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
    @Override
    protected String getLoadAllQuery() {
        return "SELECT * FROM contracts";
    }
    @Override
    protected String getLoadByIdQuery() {
        return "SELECT * FROM contracts WHERE contract_id = ? VALUES (?)";
    }
    @Override
    protected String getAddEntityQuery() {
        return "INSERT INTO contracts (employee_id, contract_type, start_date, end_date, salary) VALUES (?, ?, ?, ?, ?)";
    }
    @Override
    protected String getUpdateEntityQuery() {
        return "UPDATE contracts";
    }
    @Override
    protected String getDeleteEntityQuery() {
        return "DELETE * FROM contracts WHERE contract_id = ? VALUES (?)";
    }

    @Override
    public int addFromParams(Map<String, String[]> params) {
        int employeeId = Integer.parseInt(params.get("employeeId")[0]);
        String contractType = params.get("contractType")[0];
        Date startDate = Date.valueOf(params.get("startDate")[0]);
        Date endDate = Date.valueOf(params.get("endDate")[0]);
        float salary = Float.parseFloat(params.get("salary")[0]);

        Contract contract = new Contract(10, employeeId, contractType,startDate , endDate, salary);
        List<Object> values = Arrays.asList(
                contract.getEmployeeId(),
                contract.getContractType(),
                contract.getStartDate(),
                contract.getEndDate(),
                contract.getSalary()
        );

        return addEntity(getAddEntityQuery(), values);
    }

    private int updateContract() {
        return 0;
    }

    private boolean deleteContract() {
        return false;
    }
}
