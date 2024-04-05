package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContractsManager {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    private final int id;
    private final int employee_id;
    private final String contract_type;
    private final String start_date;
    private final String end_date;
    private final float salary;

    public ContractsManager(int id, int employee_id, String contract_type, String start_date, String end_date, float salary) {
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
                            rs.getString("start_date"),
                            rs.getString("end_date"),
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
                            rs.getString("start_date"),
                            rs.getString("end_date"),
                            rs.getFloat("salary"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return contract;
    }
}
