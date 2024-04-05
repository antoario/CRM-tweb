package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductsManager {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    private final int id;
    private final String name;
    private final String shortname;
    private final String description;
    private final float price;

    public ProductsManager(int id, String name, String shortname, String description, float price) {
        this.id = id;
        this.name = name;
        this.shortname = shortname;
        this.description = description;
        this.price = price;
    }

    public static ArrayList<ProductsManager> loadAllProducts() {
        ArrayList<ProductsManager> allProducts = new ArrayList<>();
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM crmtweb.products")) {
                ResultSet rs = st.executeQuery();
                while(rs.next()) {
                    ProductsManager temp = new ProductsManager(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("shortname"),
                            rs.getString("description"),
                            rs.getFloat("price"));
                    allProducts.add(temp);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return allProducts;
    }

    public static ProductsManager loadProductDetails(int id) {
        ProductsManager product = null;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM crmtweb.products WHERE id = ?")) {
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    product = new ProductsManager(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("shortname"),
                            rs.getString("description"),
                            rs.getFloat("price"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return product;
    }
}
