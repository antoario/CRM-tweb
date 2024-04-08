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
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();
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

    @Override
    protected String getLoadAllQuery() {
        return this.builder.getAllData();
    }

    @Override
    protected String getLoadByIdQuery() {
        return this.builder.getAllData();
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
        return this.builder.getSingle();
    }

    @Override
    public int addFromParams(Map<String, Object> params) {
        int employeeId = Integer.parseInt((String) params.get("employeeId"));
        String contractType = (String) params.get("contractType");
        Date startDate = Date.valueOf((String) params.get("startDate"));
        Date endDate = Date.valueOf((String) params.get("endDate"));
        float salary = Float.parseFloat((String) params.get("salary"));

        Contract contract = new Contract(10, employeeId, contractType, startDate, endDate, salary);
        List<Object> values = Arrays.asList(
                contract.getEmployee_id(),
                contract.getContract_type(),
                contract.getEmployee_id(),
                contract.getId(),
                contract.getSalary()
        );

        return addEntity(values);
    }

    @Override
    public int updateFromParams(Map<String, Object> params) {
        return 0;
    }


    private int updateContract() {
        return 0;
    }

    private boolean deleteContract() {
        return false;
    }
}
