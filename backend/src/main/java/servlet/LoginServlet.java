package servlet;

import java.io.*;
import java.sql.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import db.PoolingPersistenceManager;
import login.LoginService;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login", "/logout"})
public class LoginServlet extends HttpServlet {

    public static final String LOGIN_PATH = "/login";
    public static final String LOGOUT_PATH = "/logout";

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getServletPath().equals(LOGIN_PATH)) {
            String email = LoginService.getCurrentLogin(request.getSession());
            if (email.isEmpty()) sendLoginPage(request, response);
            else sendAlreadyLoggedInPage(request, response);

        } else if (request.getServletPath().equals(LOGOUT_PATH)) {
            String user = LoginService.getCurrentLogin(request.getSession());
            if (!user.isEmpty()) LoginService.doLogOut(request.getSession(), user);
            response.sendRedirect(request.getContextPath() + LOGIN_PATH);
        }
    }

    private void sendAlreadyLoggedInPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String user = LoginService.getCurrentLogin(request.getSession());
        out.println("Ao ma che fai?! Sei già loggato come: " + user);
    }

    private void sendLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("Non loggato! Qui ci sara' la pagina di login.");
    }

    private void sendWrongCredentialsPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        // time per essere rediretto alla pagina del login
        response.sendRedirect(request.getContextPath() + LOGIN_PATH);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (request.getServletPath().equals(LOGIN_PATH)) {
            String user = LoginService.getCurrentLogin(session);
            if (user.isEmpty()) {
                String username = request.getParameter("email");
                String password = request.getParameter("password");
                boolean checkCredentials = validateCredentials(username, password);
                if (checkCredentials) {
                    LoginService.doLogIn(session, username);
                    // invio Gson con dati utente
                } else sendWrongCredentialsPage(request, response);
            } else sendAlreadyLoggedInPage(request, response);
        } else response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    public boolean validateCredentials(String email, String password) {
        boolean result = false;
        try (Connection conn = PoolingPersistenceManager.getPersistenceManager().getConnection();
             PreparedStatement st = conn.prepareStatement("SELECT * FROM users " +
                     "WHERE email = ? AND password = ?");) {
            st.setString(1, email);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public void destroy() {
    }
}