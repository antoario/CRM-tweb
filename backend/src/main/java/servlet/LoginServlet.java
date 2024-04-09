package servlet;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utility.LoginHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    public void init() {}

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        LoginHelper loginHelper = new LoginHelper();
        
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> params = new Gson().fromJson(requestBody, type);

        out.println(loginHelper.loginEmailAndPassword(params.get("email"), params.get("password")));
    }

}