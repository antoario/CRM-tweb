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

@WebServlet(name = "CRMServlet", urlPatterns = {"/users/*", "/customers/*", "/providers/*", "/products/*", "/sales/*",
                                                 "/customers_activities/*", "/providers_activities/*"})
public class CRMServlet extends HttpServlet {

    public static final String USERS_PATH = "/users";
    public static final String CUSTOMERS_PATH = "/customers";
    public static final String PROVIDERS_PATH = "/providers";
    public static final String PRODUCTS_PATH = "/products";
    public static final String SALES_PATH = "/sales";
    public static final String CUSTOMERS_ACTIVITIES_PATH = "/customers_activities";
    public static final String PROVIDERS_ACTIVITIES_PATH = "/providers_activities";

    private Gson gson;

    public void init() {
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        switch(request.getServletPath()) {
            case USERS_PATH:
                out.println("Gestione di USERS");
                break;

            case CUSTOMERS_PATH:
                out.println("Gestione di CUSTOMERS");
                break;

            case PROVIDERS_PATH:
                out.println("Gestione di PROVIDERS");
                break;

            case PRODUCTS_PATH:
                out.println("Gestione di PRODUCTS");
                break;

            case SALES_PATH:
                out.println("Gestione di SALES");
                break;

            case CUSTOMERS_ACTIVITIES_PATH:
                out.println("Gestione di COSUTOMERS_ACTIVITIES");
                break;

            case PROVIDERS_ACTIVITIES_PATH:
                out.println("Gestione di PROVIDERS_ACTIVITIES");
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }


        /* TRY-CATCH per connessione al DB
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/postgres");
            try (Connection conn = ds.getConnection()) {
                out.println("Connessione al DB riuscita");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Eh no caro non sei connesso al DB");
        }*/
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

    public void destroy() {}
}
