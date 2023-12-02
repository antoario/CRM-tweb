package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
