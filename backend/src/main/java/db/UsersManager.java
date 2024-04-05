package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsersManager {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    public UsersManager() {
    }

    public static ArrayList<UsersManager> loadAllUsers() {
        return null;
    }

    public static UsersManager loadUserDetails(int id) {
        return null;
    }
}
