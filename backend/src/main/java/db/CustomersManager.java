package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomersManager {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    private final int id;
    private final String name;
    private final String surname;
    private final String email;
    private final String phone_number;
    private final String country;

    public CustomersManager(int id, String name, String surname, String email, String phone_number, String country) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone_number = phone_number;
        this.country = country;
    }
}
