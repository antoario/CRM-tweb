package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalesManager {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    private final int id;
    private final int id_product;
    private final int id_customer;
    private final int quantity;
    private final String date;

    public SalesManager(int id, int id_product, int id_customer, int quantity, String date) {
        this.id = id;
        this.id_product = id_product;
        this.id_customer = id_customer;
        this.quantity = quantity;
        this.date = date;
    }

    public static ArrayList<SalesManager> loadAllSales() {
        ArrayList<SalesManager> allSales = new ArrayList<>();
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM crmtweb.sales")) {
                ResultSet rs = st.executeQuery();
                while(rs.next()) {
                    SalesManager temp = new SalesManager(rs.getInt("id"),
                            rs.getInt("id_product"),
                            rs.getInt("id_customer"),
                            rs.getInt("quantity"),
                            rs.getString("date"));
                    allSales.add(temp);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return allSales;
    }

    public static SalesManager loadSaleDetails(int id) {
        SalesManager sale = null;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM crmtweb.sales WHERE id = ?")) {
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    sale = new SalesManager(rs.getInt("id"),
                            rs.getInt("id_product"),
                            rs.getInt("id_customer"),
                            rs.getInt("quantity"),
                            rs.getString("date"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return sale;
    }
}
