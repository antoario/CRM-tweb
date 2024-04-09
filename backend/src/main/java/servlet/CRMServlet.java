package servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import db.BaseManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utility.AuthenticationResult;
import utility.LoginHelper;
import utility.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "CRMServlet", urlPatterns = {"/employees/*", "/benefits/*", "/contracts/*", "/departments/*", "/positions/*",
        "/projects/*"})
public class CRMServlet extends HttpServlet {
    LoginHelper loginHelper = new LoginHelper();
    private Gson gson;

    private static RequestBody getRequestBody(HttpServletRequest request) throws IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        BaseManager<?> manager = ManagerFactory.getManager(request.getServletPath());
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        return new RequestBody(requestBody, manager, type);
    }

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
            out.println(manager.loadAll());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String preToken = request.getHeader("Authorization");

        AuthenticationResult result = LoginHelper.authenticate(preToken, loginHelper);
        if (!result.isSuccess()) {
            this.printResult(out, -1, result.getErrorMessage());
            return;
        }

        RequestBody body = getRequestBody(request);
        Map<String, Object> requestMap = gson.fromJson(body.requestBody(), body.type());

        switch (result.getRole()) {
            case 0:
                this.printResult(out, -1, "you don't have right role");
                return;
            case 1:
                int departmentIdFromRequest = ((Double) requestMap.get("department_id")).intValue();
                if (departmentIdFromRequest != result.getDepartment_id()) {
                    this.printResult(out, -1, "you cannot add data on different department");
                    return;
                }
        }

        try {
            out.println(body.manager().addFromParams(requestMap));
        } catch (Exception ex) {
            this.printResult(out, -1, ex.getMessage());
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        BufferedReader body = request.getReader();

        BaseManager<?> manager = ManagerFactory.getManager(request.getServletPath());
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> requestMap = gson.fromJson(body, type);

        String resultId = manager.updateFromParams(requestMap);
        out.println(resultId);
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
            out.println(manager.loadAll());
        }

    }

    public void destroy() {
    }

    private Map<String, Object> extractRequestMap(HttpServletRequest request) throws IOException {
        if ("application/json".equalsIgnoreCase(request.getContentType())) {
            String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Type type = new TypeToken<Map<String, Object>>() {
            }.getType();
            return new Gson().fromJson(requestBody, type);
        } else if ("application/x-www-form-urlencoded".equalsIgnoreCase(request.getContentType())) {
            return request.getParameterMap().entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> e.getValue()[0]
                    ));
        } else {
            throw new IOException("Unsupported Content-Type: " + request.getContentType());
        }
    }

    private void printResult(PrintWriter out, int code, String message) {
        out.println(new Gson().toJson(new Response(code, message)));
    }

    private record RequestBody(String requestBody, BaseManager<?> manager, Type type) {
    }
}
