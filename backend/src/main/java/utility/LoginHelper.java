package utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import db.PoolingPersistenceManager;

import java.sql.*;
import java.util.Objects;


public class LoginHelper {
    protected final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();
    private final String SECRET = "SUPERSECRET!";

    private String generateToken(int userId, int userRole) {
        Algorithm algorithm = Algorithm.HMAC256("VERYVERYSECRET");

        return JWT.create().withClaim("userID", userId) // Aggiungi l'ID dell'utente come claim personalizzato
                .withClaim("userRole", userRole) // Aggiungi il ruolo dell'utente come claim personalizzato
                .sign(algorithm);
    }

    public String loginEmailAndPassword(String email, String password) {
        String query = "SELECT password, id, role FROM employees WHERE email = ?";
        try (Connection conn = persistence.getConnection(); PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");

                if (Objects.equals(password, storedPassword)) {
                    int id = rs.getInt("id");
                    int role = rs.getInt("role");
                    String token = generateToken(id, role);
                    LoginResponse response = new LoginResponse(0, token, new dataLogged(id, role));
                    return new Gson().toJson(response);
                }

            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        LoginResponse response = new LoginResponse(-1, null, "Error during login");
        return new Gson().toJson(response);
    }

    public record dataLogged(int id, int role) {
    }

    public record LoginResponse(int code, String token, Object data) {
    }


}
