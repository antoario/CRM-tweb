package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomersActivitiesManager {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    private final int id;
    private final String type;
    private final String date;
    private final int responsable;
    private final int customer_id;

    public CustomersActivitiesManager(int id, String type, String date, int responsable, int customer_id) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.responsable = responsable;
        this.customer_id = customer_id;
    }

    public static ArrayList<CustomersActivitiesManager> loadAllCustomersActivities() {
        ArrayList<CustomersActivitiesManager> allCustomersActivities = new ArrayList<>();
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM crmtweb.customers_activities")) {
                ResultSet rs = st.executeQuery();
                while(rs.next()) {
                    CustomersActivitiesManager temp = new CustomersActivitiesManager(rs.getInt("id"),
                            rs.getString("type"),
                            rs.getString("date"),
                            rs.getInt("responsible"),
                            rs.getInt("customer_id"));
                    allCustomersActivities.add(temp);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return allCustomersActivities;
    }

    public static CustomersActivitiesManager loadCustomerActivityDetails(int id) {
        CustomersActivitiesManager customerActivity = null;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM crmtweb.customers_activities WHERE id = ?")) {
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    customerActivity = new CustomersActivitiesManager(rs.getInt("id"),
                            rs.getString("type"),
                            rs.getString("date"),
                            rs.getInt("responsible"),
                            rs.getInt("customer_id"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return customerActivity;
    }
}
