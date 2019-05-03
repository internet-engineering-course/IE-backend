package ir.ac.ut.joboonja.repositories;

import ir.ac.ut.joboonja.entities.Project;
import ir.ac.ut.joboonja.entities.User;

import java.util.List;

public interface ProjectRepository {
    List<Project> getAllProjects(User user);
    void insertProject(Project project);
    Project getProjectById(String id);
    List<Project> searchProjects(String filter);
    List<Project> getProjectsPaginated(User user, Integer pageNumber, Integer pageSize);
}
