package db;

import java.sql.*;
import java.util.ArrayList;

public class BenefitsManager {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    private final int id;
    private final String description;
    private final String value;
    private final int employee_id;

    public BenefitsManager(int id, String description, String value, int employee_id) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.employee_id = employee_id;
    }

    public static ArrayList<BenefitsManager> loadAllBenefits() {
        ArrayList<BenefitsManager> allBenefits = new ArrayList<>();
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM benefits")) {
                ResultSet rs = st.executeQuery();
                while(rs.next()) {
                    BenefitsManager temp = new BenefitsManager(rs.getInt("benefit_id"),
                            rs.getString("description"),
                            rs.getString("value"),
                            rs.getInt("employee_id"));
                    allBenefits.add(temp);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return allBenefits;
    }

    public static BenefitsManager loadBenefitDetails(int id) {
        BenefitsManager benefit = null;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM benefits WHERE benefit_id = ?")) {
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    benefit = new BenefitsManager(rs.getInt("benefit_id"),
                            rs.getString("description"),
                            rs.getString("value"),
                            rs.getInt("employee_id"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return benefit;
    }

    public static int addBenefit(BenefitsManager benefit) {
        int generatedId = -2;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO benefits (description, value, employee_id)" +
                    " VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                st.setString(1, benefit.description);
                st.setString(2, benefit.value);
                st.setInt(3, benefit.employee_id);
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
}
