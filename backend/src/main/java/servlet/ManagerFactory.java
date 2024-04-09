package servlet;

import db.*;

public class ManagerFactory {
    public static BaseManager<?> getManager(String path) {
        return switch (path) {
            case "/contracts" -> new ContractsManager();
            case "/employees" -> new EmployeesManager();
            case "/benefits" -> new BenefitsManager();
            case "/departments" -> new DepartmentsManager();
            case "/positions" -> new PositionsManager();
            case "/projects" -> new ProjectsManager();
            default -> throw new IllegalArgumentException("Invalid path");
        };
    }
}