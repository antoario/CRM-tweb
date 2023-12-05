package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProvidersManager {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    private final int id;
    private final String company_name;
    private final String email;
    private final String phone_number;
    private final String address;
    private final String country;

    public ProvidersManager(int id, String company_name, String email, String phone_number, String address, String country) {
        this.id = id;
        this.company_name = company_name;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.country = country;
    }

    public static ArrayList<ProvidersManager> loadAllProviders() {
        ArrayList<ProvidersManager> allProviders = new ArrayList<>();
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM crmtweb.providers")) {
                ResultSet rs = st.executeQuery();
                while(rs.next()) {
                    ProvidersManager temp = new ProvidersManager(rs.getInt("id"),
                            rs.getString("company_name"),
                            rs.getString("email"),
                            rs.getString("phone_number"),
                            rs.getString("address"),
                            rs.getString("country"));
                    allProviders.add(temp);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return allProviders;
    }

    public static ProvidersManager loadProviderDetails(int id) {
        ProvidersManager provider = null;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM crmtweb.providers WHERE id = ?")) {
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    provider = new ProvidersManager(rs.getInt("id"),
                            rs.getString("company_name"),
                            rs.getString("email"),
                            rs.getString("phone_number"),
                            rs.getString("address"),
                            rs.getString("country"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return provider;
    }
}
