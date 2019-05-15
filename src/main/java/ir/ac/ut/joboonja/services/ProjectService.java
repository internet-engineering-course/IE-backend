package ir.ac.ut.joboonja.services;

import ir.ac.ut.joboonja.entities.*;
import ir.ac.ut.joboonja.exceptions.ForbiddenException;
import ir.ac.ut.joboonja.exceptions.NotFoundException;
import ir.ac.ut.joboonja.repositories.ProjectRepository;
import ir.ac.ut.joboonja.repositories.impl.ProjectRepositoryImpl;

import java.util.LinkedList;
import java.util.List;

public class ProjectService {
    private static ProjectRepository projectRepository = new ProjectRepositoryImpl();

    private static boolean hasEnoughSkills(User user, Project project) {

        if (user == null || project == null)
            return false;

        boolean meets = true;
        for (Skill skill: project.getSkills()) {
            int skillIndex = user.getSkills().indexOf(skill);
            if (skillIndex == -1)
                meets = false;
            else if (user.getSkills().get(skillIndex).getPoint() < skill.getPoint())
                meets = false;
        }

        return meets;
    }

    private static List<Project> filterValidProjects(List<Project> allProjects, User user) {
        LinkedList<Project> result = new LinkedList<>();
        for(Project project: allProjects){
            if(hasEnoughSkills(user , project)) {
                result.add(project);
            }
        }
        return result;
    }

    public static List<Project> getValidProjects(User user) {
        return projectRepository.getAllProjects(user);
    }

    public static void insertProject(Project project) {
        projectRepository.insertProject(project);
    }

    public static List<Project> searchValidProjects(String filter, User user) {
        return projectRepository.searchProjects(filter, user);
    }

    public static List<Project> getValidProjects(User user, Integer pageNumber, Integer pageSize) {
        return projectRepository.getProjectsPaginated(user, pageNumber, pageSize);
    }

    public static List<Project> searchProjectsPaginated(String filter, Integer pageNumber, Integer pageSize, User user) {
        return projectRepository.searchProjectsPaginated(filter, pageNumber, pageSize, user);
    }

    public static Project getProjectById(String id, User user) {
        Project project = projectRepository.getProjectById(id, user);
        if (project == null)
            throw new NotFoundException("Project not found!");
        if (!hasEnoughSkills(user, project))
            throw new ForbiddenException("Access to project is forbidden!");
        return project;
    }
}
