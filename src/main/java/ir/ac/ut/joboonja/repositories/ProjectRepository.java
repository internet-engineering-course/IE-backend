package ir.ac.ut.joboonja.repositories;

import ir.ac.ut.joboonja.entities.Project;

import java.sql.SQLException;
import java.util.List;

public interface ProjectRepository {
    List<Project> getAllProjects();
    boolean projectExists(Project project);
    void insertProject(Project project) throws ClassNotFoundException, SQLException;
    Project getProject(String projectTitle);

    Project getProjectById(String id);
}
