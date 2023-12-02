package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
