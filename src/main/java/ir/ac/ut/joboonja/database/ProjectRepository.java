package ir.ac.ut.joboonja.database;

import ir.ac.ut.joboonja.entities.Project;

import java.util.List;

public interface ProjectRepository {
    List<Project> getAllProjects();
    boolean projectExists(Project project);
    void insertProject(Project project);
    Project getProject(String projectTitle);

    Project getProjectById(String id);
}
