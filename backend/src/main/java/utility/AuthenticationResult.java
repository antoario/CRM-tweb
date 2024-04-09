package utility;

public class AuthenticationResult {
    private final int role;
    private final String errorMessage;
    private final int department_id;

    public AuthenticationResult(int role, int department_id) {
        this.role = role;
        this.errorMessage = null;
        this.department_id = department_id;
    }

    public AuthenticationResult(String errorMessage) {
        this.role = -1;
        this.errorMessage = errorMessage;
        this.department_id = -1;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public int getRole() {
        return role;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isSuccess() {
        return errorMessage == null;
    }
}
