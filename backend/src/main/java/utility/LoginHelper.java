package utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.google.gson.Gson;
import db.PoolingPersistenceManager;

import java.sql.*;
import java.util.Objects;


public class LoginHelper {
    protected final static PoolingPersistenceManager persistence = PoolingPersistenceManager.getPersistenceManager();
    private final String SECRET = "SUPERSECRET!";

    public static AuthenticationResult authenticate(String token, LoginHelper loginHelper) {
        if (token == null || token.trim().isEmpty()) {
            return new AuthenticationResult("Token is missing or null.");
        }

        try {
            DecodedJWT decodedToken = loginHelper.checkLogin(token);
            int role = decodedToken.getClaim("role").asInt();
            return new AuthenticationResult(role, decodedToken.getClaim("department_id").asInt());
        } catch (JWTVerificationException ex) {
            return new AuthenticationResult("Token verification failed: " + ex.getMessage());
        } catch (Exception ex) {
            return new AuthenticationResult("Authentication failed: " + ex.getMessage());
        }
    }

    private String generateToken(int userId, int userRole, int idDepartment) {
        Algorithm algorithm = Algorithm.HMAC256("VERYVERYSECRET");

        return JWT.create().withClaim("id", userId)
                .withClaim("role", userRole)
                .withClaim("department_id", idDepartment)
                .sign(algorithm);
    }

    public String loginEmailAndPassword(String email, String password) {
        String query = "SELECT password, id, role, department_id FROM employees WHERE email = ?";
        try (Connection conn = persistence.getConnection(); PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");

                if (Objects.equals(password, storedPassword)) {
                    int id = rs.getInt("id");
                    int role = rs.getInt("role");
                    int idDepartment = rs.getInt("department_id");
                    String token = generateToken(id, role, idDepartment);
                    LoginResponse response = new LoginResponse(0, token, new dataLogged(id, role));
                    return new Gson().toJson(response);
                }

            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
            return new Gson().toJson(new LoginResponse(-1, null, ex.getMessage()));
        }
        LoginResponse response = new LoginResponse(-1, null, "Error during login");
        return new Gson().toJson(response);
    }

    public DecodedJWT checkLogin(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256("VERYVERYSECRET");

        JWTVerifier verifier = JWT.require(algorithm)
                .withClaimPresence("id")
                .withClaimPresence("role")
                .build();
        return verifier.verify(token);
    }

    public record dataLogged(int id, int role) {
    }

    public record LoginResponse(int code, String token, Object data) {
    }


}
