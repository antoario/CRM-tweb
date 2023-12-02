package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
