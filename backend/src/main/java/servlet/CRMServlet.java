package servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import db.BaseManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utility.AuthenticationResult;
import utility.ErrorHandler;
import utility.LoginHelper;
import utility.Response;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@WebServlet(name = "CRMServlet", urlPatterns = {"/employees/*", "/benefits/*", "/contracts/*", "/departments/*", "/positions/*",
        "/projects/*",})
public class CRMServlet extends HttpServlet {
    LoginHelper loginHelper = new LoginHelper();
    ErrorHandler errorHandler = new ErrorHandler();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Gson gson = new GsonBuilder().setDateFormat(sdf.toPattern()).create();

    private static RequestBody getRequestBody(HttpServletRequest request) throws IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        BaseManager<?> manager = ManagerFactory.getManager(request.getServletPath());
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        return new RequestBody(requestBody, manager, type);
    }

    public void init() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Object entity = null;

        BaseManager<?> manager = ManagerFactory.getManager(request.getServletPath());
        String idParam = request.getParameter("id");
        String roleParam = request.getParameter("role");
        String preToken = request.getHeader("Authorization");

        AuthenticationResult result = getAuthenticationResult(preToken, out);

        if (roleParam != null) {
            try {
                int role = parseInt(roleParam);
                // Todo add check inside the function
                entity = manager.loadManagerView(role);
                if (entity != null) out.println(entity);
                return;
            } catch (Exception e) {
                errorHandler.handleBadRequest(response, out, "Invalid role (doGet)");
                return;
            }
        }

        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                entity = manager.loadById(id);
                if (entity != null) out.println(gson.toJson(entity));
                else errorHandler.handleNotFound(response, out, "id not found (doGet)");
            } catch (Exception e) {
                errorHandler.handleBadRequest(response, out, "id format error (doGet)");
            }
        } else out.println(manager.loadAll());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String preToken = request.getHeader("Authorization");

        AuthenticationResult result = getAuthenticationResult(preToken, out);

        RequestBody body = getRequestBody(request);
        Map<String, Object> requestMap = gson.fromJson(body.requestBody(), body.type());

        switch (result.getRole()) {
            case 2:
                errorHandler.handleBadRequest(response, out, "Invalid role (doPost)");
                return;
            case 1:
                if (requestMap.get("department_id") != null) {
                    int departmentIdFromRequest = ((Double) requestMap.get("department_id")).intValue();
                    if (departmentIdFromRequest != result.getDepartment_id()) {
                        errorHandler.handleBadRequest(response, out, "you cannot add data on different department (doPost)");
                        return;
                    }
                }
        }

        try {
            out.println(body.manager().addFromParams(requestMap));
        } catch (Exception ex) {
            errorHandler.handleBadRequest(response, out, "error adding data (doPost)");
        }
    }

    private AuthenticationResult getAuthenticationResult(String preToken, PrintWriter out) {
        AuthenticationResult result = LoginHelper.authenticate(preToken, loginHelper);
        if (!result.isSuccess()) {
            errorHandler.commonError(out, "error authenticating user");
        }
        return result;
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        BufferedReader body = request.getReader();

        BaseManager<?> manager = ManagerFactory.getManager(request.getServletPath());
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> requestMap = gson.fromJson(body, type);

        String resultId = null;
        try {
            resultId = manager.updateFromParams(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
            errorHandler.handleBadRequest(response, out, e.getMessage());
            return;
        }
        out.println(resultId);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        int idParam = parseInt(request.getParameter("id"));
        String preToken = request.getHeader("Authorization");

        AuthenticationResult result = getAuthenticationResult(preToken, out);

        if (result.getRole() == 2) {
            errorHandler.handleBadRequest(response, out, "not right role (doDelete)");
        }

        RequestBody body = getRequestBody(request);

        try {
            body.manager.deleteEntity(idParam);
            out.println(new Gson().toJson(new Response(0, "Successfully deleted")));
        } catch (Exception ex) {
            errorHandler.handleBadRequest(response, out, "error deleting entity (doDelete)");
        }

    }

    private record RequestBody(String requestBody, BaseManager<?> manager, Type type) {
    }
}
