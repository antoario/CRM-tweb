package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsersManager {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    private int id;
    private String name;
    private String surname;
    private String email;
    private String phone_number;
    private String password;
    private String role;

    public UsersManager(int id, String name, String surname, String email, String phone_number, String password, String role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone_number = phone_number;
        this.password = password;
        this.role = role;
    }

    public static UsersManager validateCredentials(String email, String password) {
        UsersManager user = null;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM crmtweb.users WHERE email = ? AND password = ?")) {
                st.setString(1, email);
                st.setString(2, password);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    user = new UsersManager(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("email"),
                            rs.getString("phone_number"),
                            rs.getString("password"),
                            rs.getString("role"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return user;
    }

    public static ArrayList<UsersManager> loadAllUsers() {
        ArrayList<UsersManager> allUsers = new ArrayList<>();
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM crmtweb.users")) {
                ResultSet rs = st.executeQuery();
                while(rs.next()) {
                    UsersManager temp = new UsersManager(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("email"),
                            rs.getString("phone_number"),
                            rs.getString("password"),
                            rs.getString("role"));
                    allUsers.add(temp);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return allUsers;
    }

    public static UsersManager loadUserDetails(int id) {
        UsersManager user = null;
        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM crmtweb.users WHERE id = ?")) {
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    user = new UsersManager(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("email"),
                            rs.getString("phone_number"),
                            rs.getString("password"),
                            rs.getString("role"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return user;
    }
}
