package db;

import java.sql.*;
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

    public static int addUser(UsersManager user) {
        return -2;
    }
}
