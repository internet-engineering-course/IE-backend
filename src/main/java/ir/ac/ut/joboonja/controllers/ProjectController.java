package ir.ac.ut.joboonja.controllers;

import ir.ac.ut.joboonja.command.Commands;
import ir.ac.ut.joboonja.entities.Project;
import ir.ac.ut.joboonja.entities.User;
import ir.ac.ut.joboonja.exceptions.ForbiddenException;
import ir.ac.ut.joboonja.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @GetMapping
    public List<Project> getProjects() {
        return Commands.getValidProjects(Commands.getDefaultUser());
    }

    @GetMapping("/{projectId}")
    public Project getProject(@PathVariable("projectId") String projectId) {
        User user = Commands.getDefaultUser();
        Project project = Commands.getProjectById(projectId);
        if (project == null)
            throw new NotFoundException("Project not found!");
        else if (Commands.hasEnoughSkills(user, project))
            return project;
        else
            throw new ForbiddenException("Access to project is forbidden!");
    }
}
