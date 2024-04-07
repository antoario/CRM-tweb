package servlet;

import db.*;

public class ManagerFactory {
    public static BaseManager<?> getManager(String path) {
        switch (path) {
            case "/contracts":
                return new ContractsManager();
            case "/employees":
                return new EmployeesManager();
            case "/benefits":
                return new BenefitsManager();
            case "/departments":
                return new DepartmentsManager();
            case "/positions":
                return new PositionsManager();
            case "/projects":
                return new ProjectsManager();
            default:
                throw new IllegalArgumentException("Invalid path");
        }
    }
}