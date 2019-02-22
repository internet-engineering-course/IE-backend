package database;

import entities.Project;

import java.util.List;

public interface ProjectRepository {
//    List<Project> getAllProjects();
    boolean projectExists(Project project);
    void insertProject(Project project);
    Project getProject(String projectTitle);
}
