package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersManager {
    private final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();

    private final int id;
    private final String name;
    private final String surname;
    private final String email;
    private final String phone_number;
    private final String password;
    private final String role;

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
        UsersManager ret = null;

        try (Connection conn = persistence.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?")) {
                st.setString(1, email);
                st.setString(2, password);

                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    ret = new UsersManager(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("email"),
                            rs.getString("phone_number"),
                            rs.getString("password"),
                            rs.getString("role"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return ret;
    }
}
