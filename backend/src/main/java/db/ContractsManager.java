package db;

import Data.Contract;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ContractsManager extends BaseManager<Contract>{
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    public ContractsManager() {}

    @Override
    public int addFromParams(Map<String, String[]> params) {

        int employeeId = Integer.parseInt(params.get("employeeId")[0]);
        String contractType = params.get("contractType")[0];
        LocalDate startDate = LocalDate.parse(params.get("startDate")[0]);
        LocalDate endDate = LocalDate.parse(params.get("endDate")[0]);
        float salary = Float.parseFloat(params.get("salary")[0]);

        Contract contract = new Contract(10, employeeId, contractType,startDate , endDate, salary);

        // Supponi di avere un metodo add che accetti un'entità e restituisca un ID
        String query = "INSERT INTO contracts (employee_id, contract_type, start_date, end_date, salary) VALUES (?, ?, ?, ?, ?)";
        List<Object> values = Arrays.asList(
                contract.getEmployeeId(),
                contract.getContractType(),
                contract.getStartDate(),
                contract.getEndDate(),
                contract.getSalary()
        );

        return addEntity(query, values);
    }

    public static int editContract(ContractsManager contract) {
        // uguale alla post cambiando insert e aggiungendo i dati
        return 0;
    }

    @Override
    protected Contract mapRowToEntity(ResultSet rs) throws SQLException {
        return new Contract(
                rs.getInt("id"),
                rs.getInt("employee_id"),
                rs.getString("contract_type"),
                rs.getDate("start_date").toLocalDate(),
                rs.getDate("end_date").toLocalDate(),
                rs.getFloat("salary"));
    }

    @Override
    protected String getLoadAllQuery() {
        return "SELECT * FROM contracts";
    }

    @Override
    public Contract loadDetails(int id) {
        String query = "SELECT * FROM contracts WHERE id = ?";
        return loadById(query, id);
    }
}
