package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Data.Contract;
import com.google.gson.Gson;
import db.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "CRMServlet", urlPatterns = {"/employees/*", "/benefits/*", "/contracts/*", "/departments/*", "/positions/*",
                                                 "/projects/*"})
public class CRMServlet extends HttpServlet {

    public static final String EMPLOYEES_PATH = "/employees";
    public static final String BENEFITS_PATH = "/benefits";
    public static final String CONTRACTS_PATH = "/contracts";
    public static final String DEPARTMENTS_PATH = "/departments";
    public static final String POSITIONS_PATH = "/positions";
    public static final String PROJECTS_PATH = "/projects";

    private Gson gson;

    public void init() {
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Ottieni il manager appropriato per il percorso della richiesta
        BaseManager<?> manager = ManagerFactory.getManager(request.getServletPath());

        // Ottieni i parametri della richiesta
        String idParam = request.getParameter("id");
        System.out.println(idParam);

        if (idParam != null) {
            // Gestione del caricamento dei dettagli di un'entità specifica
            try {
                int id = Integer.parseInt(idParam);
                Object entity = manager.loadDetails(id);
                if (entity != null) {
                    out.println(gson.toJson(entity));
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            // Gestione del caricamento di tutte le entità
            List<?> entities = manager.loadAll();
            out.println(gson.toJson(entities));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Ottieni il manager appropriato
        GenericManager<?> manager = ManagerFactory.getManager(request.getServletPath());

        // Chiama addFromParams passando i parametri della richiesta
        int resultId = manager.addFromParams(request.getParameterMap());

        // Restituisci l'ID risultante o un errore se l'ID è -1
        if (resultId != -1) {
            out.println(gson.toJson(resultId));
        } else {
            out.println(gson.toJson(-1)); // Indica un fallimento
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // SIMILE ALLA POST PER I PARAMETRI
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        BufferedReader body = request.getReader();

        switch(request.getServletPath()) {
            case EMPLOYEES_PATH:
                if(body != null && body.ready()) {
                    EmployeesManager employee = gson.fromJson(body, EmployeesManager.class);
                    int employeeId = EmployeesManager.editEmployee(employee);
                    out.println(gson.toJson(employeeId));
                } else {
                    out.println(gson.toJson(-1));
                }
                break;

            case BENEFITS_PATH:
                if(body != null && body.ready()) {
                    BenefitsManager benefit = gson.fromJson(body, BenefitsManager.class);
                    int benefitId = BenefitsManager.editBenefit(benefit);
                    out.println(gson.toJson(benefitId));
                } else {
                    out.println(gson.toJson(-1));
                }
                break;

            case CONTRACTS_PATH:
                if(body != null && body.ready()) {
                    ContractsManager contract = gson.fromJson(body, ContractsManager.class);
                    int contractId = ContractsManager.editContract(contract);
                    out.println(gson.toJson(contractId));
                } else {
                    out.println(gson.toJson(-1));
                }
                break;

            case DEPARTMENTS_PATH:
                if(body != null && body.ready()) {
                    DepartmentsManager department = gson.fromJson(body, DepartmentsManager.class);
                    int departmentId = DepartmentsManager.editDepartment(department);
                    out.println(gson.toJson(departmentId));
                } else {
                    out.println(gson.toJson(-1));
                }
                break;

            case POSITIONS_PATH:
                if(body != null && body.ready()) {
                    PositionsManager position = gson.fromJson(body, PositionsManager.class);
                    int positionId = PositionsManager.editPosition(position);
                    out.println(gson.toJson(positionId));
                } else {
                    out.println(gson.toJson(-1));
                }
                break;

            case PROJECTS_PATH:
                if(body != null && body.ready()) {
                    ProjectsManager project = gson.fromJson(body, ProjectsManager.class);
                    int projectId = ProjectsManager.editProject(project);
                    out.println(gson.toJson(projectId));
                } else {
                    out.println(gson.toJson(-1));
                }
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

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void destroy() {}
}
