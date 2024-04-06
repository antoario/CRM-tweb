package db;

import java.sql.*;
import java.util.ArrayList;

public class ContractsManager {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    private final int id;
    private final int employee_id;
    private final String contract_type;
    private final Date start_date;
    private final Date end_date;
    private final float salary;

    public ContractsManager(int id, int employee_id, String contract_type, Date start_date, Date end_date, float salary) {
        this.id = id;
        this.employee_id = employee_id;
        this.contract_type = contract_type;
        this.start_date = start_date;
        this.end_date = end_date;
        this.salary = salary;
    }

    public static ArrayList<ContractsManager> loadAllContracts() {
        ArrayList<ContractsManager> allContracts = new ArrayList<>();
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM contracts")) {
                ResultSet rs = st.executeQuery();
                while(rs.next()) {
                    ContractsManager temp = new ContractsManager(rs.getInt("contract_id"),
                            rs.getInt("employee_id"),
                            rs.getString("contract_type"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date"),
                            rs.getFloat("salary"));
                    allContracts.add(temp);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return allContracts;
    }

    public static ContractsManager loadContractDetails(int id) {
        ContractsManager contract = null;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM contracts WHERE contract_id = ?")) {
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    contract = new ContractsManager(rs.getInt("contract_id"),
                            rs.getInt("employee_id"),
                            rs.getString("contract_type"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date"),
                            rs.getFloat("salary"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return contract;
    }

    public static int addContract(ContractsManager contract) {
        int generatedId = -2;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO contracts (employee_id, contract_type, start_date, end_date, salary)" +
                    " VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                st.setInt(1, contract.employee_id);
                st.setString(2, contract.contract_type);
                st.setDate(3, contract.start_date);
                st.setDate(4, contract.end_date);
                st.setFloat(5, contract.salary);
                st.executeUpdate();

                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return generatedId;
    }

    public static int editContract(ContractsManager contract) {
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("UPDATE contracts SET employee_id = ?, contract_type = ?, start_date = ?, " +
                    "end_date = ?, salary = ? WHERE contract_id = ?")) {
                st.setInt(1, contract.employee_id);
                st.setString(2, contract.contract_type);
                st.setDate(3, contract.start_date);
                st.setDate(4, contract.end_date);
                st.setFloat(5, contract.salary);
                st.setInt(6, contract.id);
                st.executeUpdate();
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return contract.id;
    }
}
