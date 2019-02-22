package database.impl;

import database.ProjectRepository;
import entities.Project;

import java.util.List;


public class ProjectRepositoryInMemoryImpl implements ProjectRepository {
    @Override
    public List<Project> getAllProjects() {
        return MemoryDataBase.getInstance().getAllProjects();
    }

    @Override
    public boolean projectExists(Project project) {
        return MemoryDataBase.getInstance().projectExists(project);
    }

    @Override
    public void insertProject(Project project){
        MemoryDataBase.getInstance().insertProject(project);
    }

    @Override
    public Project getProject(String projectTitle) {
        return MemoryDataBase.getInstance().getProject(projectTitle);
    }

}
