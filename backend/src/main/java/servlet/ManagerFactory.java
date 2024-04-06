package servlet;

import db.BaseManager;
import db.BenefitsManager;
import db.ContractsManager;
import db.EmployeesManager;

public class ManagerFactory {
    public static BaseManager<?> getManager(String path) {
        switch (path) {
            case "/contracts":
                return new ContractsManager();
            // Aggiungi altri casi qui
            default:
                throw new IllegalArgumentException("Invalid path");
        }
    }
}