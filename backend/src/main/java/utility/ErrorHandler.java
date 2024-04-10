package utility;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ErrorHandler {
    private final Gson gson;

    public ErrorHandler() {
        this.gson = new Gson();
    }

    public void sendError(PrintWriter out, String message) {
        out.println(gson.toJson(new Response(-1, message)));
    }

    public void handleNotFound(HttpServletResponse response, PrintWriter out, String message) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        sendError(out, message);
    }

    public void handleBadRequest(HttpServletResponse response, PrintWriter out, String message) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        sendError(out, message);
    }

    public void commonError(PrintWriter out, String message) {
        sendError(out, message);
    }
}
