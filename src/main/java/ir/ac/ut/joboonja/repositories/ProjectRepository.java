package ir.ac.ut.joboonja.repositories;

import ir.ac.ut.joboonja.entities.Project;

import java.sql.SQLException;
import java.util.List;

public interface ProjectRepository {
    List<Project> getAllProjects();
    void insertProject(Project project);
    Project getProjectById(String id);
    List<Project> searchProjects(String filter);
    List<Project> getProjectsPaginated(Integer pageNumber, Integer pageSize);
}
