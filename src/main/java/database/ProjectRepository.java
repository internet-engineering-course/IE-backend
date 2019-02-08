package database;

import entities.Project;

public interface ProjectRepository {
    boolean projectExists(Project project);
    void insertProject(Project project);
    Project getProject(String projectTitle);
}
