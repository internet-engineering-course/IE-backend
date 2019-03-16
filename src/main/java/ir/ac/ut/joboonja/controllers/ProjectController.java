package ir.ac.ut.joboonja.controllers;

import ir.ac.ut.joboonja.command.Commands;
import ir.ac.ut.joboonja.entities.Project;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/project")
public class ProjectController {

    @GetMapping
    public List<Project> getProjects() {
        return Commands.getValidProjects(Commands.getDefaultUser());
    }
}
