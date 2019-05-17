package ir.ac.ut.joboonja.repositories;

import ir.ac.ut.joboonja.entities.Project;
import ir.ac.ut.joboonja.entities.User;

import java.util.List;

public interface ProjectRepository {
    List<Project> getAllProjects(User user);
    List<Project> getAllProjects();
    void insertProject(Project project);
    Project getProjectById(String id, User user);
    List<Project> searchProjects(String filter, User user);

    List<Project> searchProjectsPaginated(String filter, Integer pageNumber, Integer pageSize, User user);
    List<Project> getProjectsPaginated(User user, Integer pageNumber, Integer pageSize);
}
