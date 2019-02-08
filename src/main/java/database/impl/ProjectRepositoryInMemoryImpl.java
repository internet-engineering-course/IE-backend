package database.impl;

import database.ProjectRepository;
import entities.Project;


public class ProjectRepositoryInMemoryImpl implements ProjectRepository {
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
