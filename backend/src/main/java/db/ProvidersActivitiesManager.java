package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProvidersActivitiesManager {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    private final int id;
    private final String type;
    private final String date;
    private final int responsable;
    private final int provider_id;

    public ProvidersActivitiesManager(int id, String type, String date, int responsable, int provider_id) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.responsable = responsable;
        this.provider_id = provider_id;
    }

    public static ArrayList<ProvidersActivitiesManager> loadAllProvidersActivities() {
        ArrayList<ProvidersActivitiesManager> allProvidersActivities = new ArrayList<>();
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM crmtweb.providers_activities")) {
                ResultSet rs = st.executeQuery();
                while(rs.next()) {
                    ProvidersActivitiesManager temp = new ProvidersActivitiesManager(rs.getInt("id"),
                            rs.getString("type"),
                            rs.getString("date"),
                            rs.getInt("responsible"),
                            rs.getInt("provider_id"));
                    allProvidersActivities.add(temp);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return allProvidersActivities;
    }

    public static ProvidersActivitiesManager loadProviderActivityDetails(int id) {
        ProvidersActivitiesManager providerActivity = null;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM crmtweb.providers_activities WHERE id = ?")) {
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    providerActivity = new ProvidersActivitiesManager(rs.getInt("id"),
                            rs.getString("type"),
                            rs.getString("date"),
                            rs.getInt("responsible"),
                            rs.getInt("provider_id"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return providerActivity;
    }
}
