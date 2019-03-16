package ir.ac.ut.joboonja.controllers;

import ir.ac.ut.joboonja.command.Commands;
import ir.ac.ut.joboonja.entities.Project;
import ir.ac.ut.joboonja.entities.User;
import ir.ac.ut.joboonja.exceptions.BadRequestException;
import ir.ac.ut.joboonja.exceptions.ForbiddenException;
import ir.ac.ut.joboonja.exceptions.NotFoundException;
import ir.ac.ut.joboonja.models.BidAmount;
import ir.ac.ut.joboonja.models.BidInfo;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{projectId}/bid")
    public BidInfo bidProject(@PathVariable("projectId") String projectId, @RequestBody BidAmount bidAmount) {
        User user = Commands.getDefaultUser();
        Project project = Commands.getProjectById(projectId);
        if (project == null)
            throw new NotFoundException("Project not found!");
        else if (Commands.userIsBidBefore(project, user))
            throw new BadRequestException("User has already bidded!");
        else if (!Commands.hasEnoughSkills(user, project))
            throw new ForbiddenException("Access to project is forbidden!");
        return Commands.addBid(project, user, bidAmount.getBidAmount());
    }
}
