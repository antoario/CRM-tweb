package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomersActivitiesManager {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    private final int id;
    private final String type;
    private final String date;
    private final int id_user;
    private final int id_customer;

    public CustomersActivitiesManager(int id, String type, String date, int id_user, int id_customer) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.id_user = id_user;
        this.id_customer = id_customer;
    }
}
