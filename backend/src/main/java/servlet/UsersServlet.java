package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

@WebServlet(name = "UsersServlet", urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet {

    private Gson gson;

    public void init() {
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/postgres");
            try (Connection conn = ds.getConnection()) {
                out.println("Connessione al server riuscita");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Eh no caro non sei connesso al Server");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

    public void destroy() {}
}
