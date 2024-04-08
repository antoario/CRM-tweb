package utility;

public class Response {
    int code;
    Object data;

    public Response(int code, Object data) {
        this.code = code;
        this.data = data;
    }
}
