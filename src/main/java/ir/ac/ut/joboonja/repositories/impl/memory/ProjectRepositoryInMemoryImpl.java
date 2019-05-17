package ir.ac.ut.joboonja.repositories.impl.memory;

import ir.ac.ut.joboonja.entities.User;
import ir.ac.ut.joboonja.repositories.ProjectRepository;
import ir.ac.ut.joboonja.entities.Project;

import java.util.List;


public class ProjectRepositoryInMemoryImpl implements ProjectRepository {
    @Override
    public List<Project> getAllProjects(User user) {
        return MemoryDataBase.getInstance().getAllProjects();
    }

    @Override
    public List<Project> getAllProjects() {
        return null;
    }

    @Override
    public void insertProject(Project project){
        MemoryDataBase.getInstance().insertProject(project);
    }

    @Override
    public Project getProjectById(String id, User user) {
        return MemoryDataBase.getInstance().getProjectById(id);
    }

    @Override
    public List<Project> searchProjects(String filter, User user) {
        return null;
    }

    @Override
    public List<Project> searchProjectsPaginated(String filter, Integer pageNumber, Integer pageSize, User user) {
        return null;
    }

    @Override
    public List<Project> getProjectsPaginated(User user, Integer pageNumber, Integer pageSize) {
        return null;
    }

}
