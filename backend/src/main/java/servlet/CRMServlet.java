package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.sql.Connection;
import java.util.Map;

import com.google.gson.Gson;
import db.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import login.LoginService;

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
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Map<String, String[]> pars = request.getParameterMap();

        switch(request.getServletPath()) {
            case USERS_PATH:
                if (pars.containsKey("id")) {
                    int userId = Integer.parseInt(pars.get("id")[0]);
                    UsersManager user = UsersManager.loadUserDetails(userId);
                    out.println(gson.toJson(user));
                } else {
                    ArrayList<UsersManager> allUsers = UsersManager.loadAllUsers();
                    out.println(gson.toJson(allUsers));
                }
                break;

            case CUSTOMERS_PATH:
                if (pars.containsKey("id")) {
                    int customerId = Integer.parseInt(pars.get("id")[0]);
                    CustomersManager customer = CustomersManager.loadCustomerDetails(customerId);
                    out.println(gson.toJson(customer));
                } else {
                    ArrayList<CustomersManager> allCustomers = CustomersManager.loadAllCustomers();
                    out.println(gson.toJson(allCustomers));
                }
                break;

            case PROVIDERS_PATH:
                if (pars.containsKey("id")) {
                    int providerId = Integer.parseInt(pars.get("id")[0]);
                    ProvidersManager provider = ProvidersManager.loadProviderDetails(providerId);
                    out.println(gson.toJson(provider));
                } else {
                    ArrayList<ProvidersManager> allProviders = ProvidersManager.loadAllProviders();
                    out.println(gson.toJson(allProviders));
                }
                break;

            case PRODUCTS_PATH:
                if (pars.containsKey("id")) {
                    int productId = Integer.parseInt(pars.get("id")[0]);
                    ProductsManager product = ProductsManager.loadProductDetails(productId);
                    out.println(gson.toJson(product));
                } else {
                    ArrayList<ProductsManager> allProducts = ProductsManager.loadAllProducts();
                    out.println(gson.toJson(allProducts));
                }
                break;

            case SALES_PATH:
                if (pars.containsKey("id")) {
                    int saleId = Integer.parseInt(pars.get("id")[0]);
                    SalesManager sale = SalesManager.loadSaleDetails(saleId);
                    out.println(gson.toJson(sale));
                } else {
                    ArrayList<SalesManager> allSales = SalesManager.loadAllSales();
                    out.println(gson.toJson(allSales));
                }
                break;

            case CUSTOMERS_ACTIVITIES_PATH:
                if (pars.containsKey("id")) {
                    int customerActivityId = Integer.parseInt(pars.get("id")[0]);
                    CustomersActivitiesManager customerActivity = CustomersActivitiesManager.loadCustomerActivityDetails(customerActivityId);
                    out.println(gson.toJson(customerActivity));
                } else {
                    ArrayList<CustomersActivitiesManager> allCustomersActivities = CustomersActivitiesManager.loadAllCustomersActivities();
                    out.println(gson.toJson(allCustomersActivities));
                }
                break;

            case PROVIDERS_ACTIVITIES_PATH:
                if (pars.containsKey("id")) {
                    int providerActivityId = Integer.parseInt(pars.get("id")[0]);
                    ProvidersActivitiesManager provider_activity = ProvidersActivitiesManager.loadProviderActivityDetails(providerActivityId);
                    out.println(gson.toJson(provider_activity));
                } else {
                    ArrayList<ProvidersActivitiesManager> allProvidersActivities = ProvidersActivitiesManager.loadAllProvidersActivities();
                    out.println(gson.toJson(allProvidersActivities));
                }
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        BufferedReader body = request.getReader();

        switch(request.getServletPath()) {
            case USERS_PATH:
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
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        switch(request.getServletPath()) {
            case USERS_PATH:
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
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        switch(request.getServletPath()) {
            case USERS_PATH:
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
    }

    public void destroy() {}
}
