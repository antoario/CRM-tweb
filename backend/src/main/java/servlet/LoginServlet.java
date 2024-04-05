package servlet;

import java.io.*;

import com.google.gson.Gson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import db.*;
import login.LoginService;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login", "/logout"})
public class LoginServlet extends HttpServlet {

    private static final String LOGIN_PATH = "/login";
    private static final String LOGOUT_PATH = "/logout";

    private Gson gson;

    public void init() {
        gson = new Gson();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        boolean res;

        if (request.getServletPath().equals(LOGOUT_PATH)) {
            String user = LoginService.getCurrentLogin(request.getSession());
            res = LoginService.doLogOut(request.getSession(), user);
            out.println(gson.toJson(res));

        } else response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        if (request.getServletPath().equals(LOGIN_PATH)) {
            String user = LoginService.getCurrentLogin(session);

            if (user.isEmpty()) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                EmployeesManager userData = EmployeesManager.validateCredentials(email, password);

                if(userData != null) {
                    LoginService.doLogIn(session, email);
                    out.println(gson.toJson(userData));
                } else sendWrongCredentials(request, response);

            } else sendAlreadyLogged(request, response);

        } else response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void sendAlreadyLogged(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String user = LoginService.getCurrentLogin(request.getSession());
        out.println(gson.toJson(user));
    }

    private void sendWrongCredentials(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    public void destroy() {
    }
}