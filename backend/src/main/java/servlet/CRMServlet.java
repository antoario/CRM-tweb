package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import com.google.gson.Gson;
import db.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "CRMServlet", urlPatterns = {"/employees/*", "/benefits/*", "/contracts/*", "/departments/*", "/positions/*",
                                                 "/projects/*", "/users/*"})
public class CRMServlet extends HttpServlet {

    public static final String EMPLOYEES_PATH = "/employees";
    public static final String BENEFITS_PATH = "/benefits";
    public static final String CONTRACTS_PATH = "/contracts";
    public static final String DEPARTMENTS_PATH = "/departments";
    public static final String POSITIONS_PATH = "/positions";
    public static final String PROJECTS_PATH = "/projects";
    public static final String USERS_PATH = "/users";

    private Gson gson;

    public void init() {
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Map<String, String[]> pars = request.getParameterMap();

        switch(request.getServletPath()) {
            case EMPLOYEES_PATH:
                if (pars.containsKey("id")) {
                    int employeeId = Integer.parseInt(pars.get("id")[0]);
                    EmployeesManager user = EmployeesManager.loadEmployeeDetails(employeeId);
                    out.println(gson.toJson(user));
                } else {
                    ArrayList<EmployeesManager> allUsers = EmployeesManager.loadAllEmployees();
                    out.println(gson.toJson(allUsers));
                }
                break;

            case BENEFITS_PATH:
                if (pars.containsKey("id")) {
                    int benefitId = Integer.parseInt(pars.get("id")[0]);
                    BenefitsManager benefit = BenefitsManager.loadBenefitDetails(benefitId);
                    out.println(gson.toJson(benefit));
                } else {
                    ArrayList<BenefitsManager> allBenefits = BenefitsManager.loadAllBenefits();
                    out.println(gson.toJson(allBenefits));
                }
                break;

            case CONTRACTS_PATH:
                if (pars.containsKey("id")) {
                    int contractId = Integer.parseInt(pars.get("id")[0]);
                    ContractsManager contract = ContractsManager.loadContractDetails(contractId);
                    out.println(gson.toJson(contract));
                } else {
                    ArrayList<ContractsManager> allContracts = ContractsManager.loadAllContracts();
                    out.println(gson.toJson(allContracts));
                }
                break;

            case DEPARTMENTS_PATH:
                if (pars.containsKey("id")) {
                    int departmentId = Integer.parseInt(pars.get("id")[0]);
                    DepartmentsManager department = DepartmentsManager.loadDepartmentDetails(departmentId);
                    out.println(gson.toJson(department));
                } else {
                    ArrayList<DepartmentsManager> allDepartments = DepartmentsManager.loadAllDepartments();
                    out.println(gson.toJson(allDepartments));
                }
                break;

            case POSITIONS_PATH:
                if (pars.containsKey("id")) {
                    int positionId = Integer.parseInt(pars.get("id")[0]);
                    PositionsManager position = PositionsManager.loadPositionDetails(positionId);
                    out.println(gson.toJson(position));
                } else {
                    ArrayList<PositionsManager> allPositions = PositionsManager.loadAllPositions();
                    out.println(gson.toJson(allPositions));
                }
                break;

            case PROJECTS_PATH:
                if (pars.containsKey("id")) {
                    int projectId = Integer.parseInt(pars.get("id")[0]);
                    ProjectsManager project = ProjectsManager.loadProjectDetails(projectId);
                    out.println(gson.toJson(project));
                } else {
                    ArrayList<ProjectsManager> allProjects = ProjectsManager.loadAllProjects();
                    out.println(gson.toJson(allProjects));
                }
                break;

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

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        BufferedReader body = request.getReader();

        switch(request.getServletPath()) {
            case EMPLOYEES_PATH:
            case BENEFITS_PATH:
            case CONTRACTS_PATH:
            case DEPARTMENTS_PATH:
            case POSITIONS_PATH:
            case PROJECTS_PATH:
            case USERS_PATH:
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        switch(request.getServletPath()) {
            case EMPLOYEES_PATH:
            case BENEFITS_PATH:
            case CONTRACTS_PATH:
            case DEPARTMENTS_PATH:
            case POSITIONS_PATH:
            case PROJECTS_PATH:
            case USERS_PATH:
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        switch(request.getServletPath()) {
            case EMPLOYEES_PATH:
            case BENEFITS_PATH:
            case CONTRACTS_PATH:
            case DEPARTMENTS_PATH:
            case POSITIONS_PATH:
            case PROJECTS_PATH:
            case USERS_PATH:
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void destroy() {}
}
