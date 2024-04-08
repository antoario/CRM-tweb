package servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import db.BaseManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utility.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

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
        System.out.println(idParam);

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

        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        BaseManager<?> manager = ManagerFactory.getManager(request.getServletPath());

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> requestMap = gson.fromJson(requestBody, type);


        String res = manager.addFromParams(requestMap);

        out.println(res);
    }


    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        BufferedReader body = request.getReader();

        BaseManager<?> manager = ManagerFactory.getManager(request.getServletPath());
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        BaseManager<?> manager = ManagerFactory.getManager(request.getServletPath());
        String idParam = request.getParameter("id");

        if (idParam != null) {
            try {
                Object entity = manager.deleteEntity(idParam);
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

}
