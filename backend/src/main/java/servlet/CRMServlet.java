package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import com.google.gson.Gson;
import db.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "CRMServlet", urlPatterns = {"/employees/*", "/benefits/*", "/contracts/*", "/departments/*", "/positions/*",
                                                 "/projects/*"})
public class CRMServlet extends HttpServlet {
    private Gson gson;

    public void init() {
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        BaseManager<?> manager = ManagerFactory.getManager(request.getServletPath());
        String idParam = request.getParameter("id");

        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                Object entity = manager.loadById(id);
                if (entity != null) out.println(gson.toJson(entity));
                else response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            ArrayList<?> entities = manager.loadAll();
            out.println(gson.toJson(entities));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        BaseManager<?> manager = ManagerFactory.getManager(request.getServletPath());
        int resultId = manager.addFromParams(request.getParameterMap());

        if (resultId != -1) out.println(gson.toJson(resultId));
        else out.println(gson.toJson(-1));
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // SIMILE ALLA POST PER I PARAMETRI
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        BaseManager<?> manager = ManagerFactory.getManager(request.getServletPath());


    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();


    }

    public void destroy() {}
}
