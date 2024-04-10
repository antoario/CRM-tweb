package db;

import Data.Project;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ProjectsManager extends BaseManager<Project> {
    public ProjectsManager() {
    }

    @Override
    protected Project mapRowToEntity(ResultSet rs) throws SQLException {
        return new Project(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("start_date"),
                rs.getDate("end_date"),
                rs.getInt("department_id"));
    }


    protected String getAddEntityQuery() {
        return "INSERT INTO projects (name,  description, start_date, end_date, department_id) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getLoadAllQuery() {
        return "SELECT * FROM projects";
    }

    @Override
    protected String getLoadAllManagerQuery() {
        return "SELECT * FROM projects WHERE department_id = ?";
    }

    @Override
    protected String getLoadByIdQuery() {
        return "SELECT * FROM projects WHERE id = ?";
    }

    @Override
    protected String getUpdateEntityQuery() {
        return "UPDATE projects SET name = ?, description = ?, start_date = ?, end_date = ?, department_id = ? WHERE id = ?";
    }

    @Override
    protected String getDeleteEntityQuery() {
        return "DELETE FROM projects WHERE id = ?";
    }

    @Override
    protected List<Object> getUpdateFromParams(Map<String, Object> params) {
        Integer id;
        Date endDate;
        try {
            id = (int) ((Double) params.get("id")).doubleValue();
        } catch (Exception ex) {
            id = null;
        }

        String name = (String) params.get("name");
        String description = (String) params.get("description");
        Date startDate = Date.valueOf((String) params.get("start_date"));
        try {
            endDate = Date.valueOf((String) params.get("end_date"));
        } catch (Exception ex) {
            endDate = null;
        }

        int idDepartment = (int) ((Double) params.get("department_id")).doubleValue();

        return Arrays.asList(
                name,
                description,
                startDate,
                endDate,
                idDepartment,
                id
        );
    }
}
