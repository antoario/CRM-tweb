package servlet;

import Data.Employee;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import db.BaseManager;
import db.EmployeesManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utility.LoginHelper;
import utility.Response;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "meServlet", urlPatterns = {"/me"})
public class meServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        LoginHelper loginHelper = new LoginHelper();
        BaseManager<Employee> employeesManager = new EmployeesManager();
        try {
            String preToken = request.getHeader("Authorization");
            DecodedJWT token = loginHelper.checkLogin(preToken);

            Employee employee = employeesManager.loadById(token.getClaim("id").asInt());
            out.println(new Gson().toJson(employee));
        } catch (Exception ex) {
            out.println(new Gson().toJson(new Response(-1, ex.getMessage())));
            ex.printStackTrace();
        }
    }
}
