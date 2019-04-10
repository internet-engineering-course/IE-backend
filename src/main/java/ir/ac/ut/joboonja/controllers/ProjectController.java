package ir.ac.ut.joboonja.controllers;

import ir.ac.ut.joboonja.command.Commands;
import ir.ac.ut.joboonja.entities.Project;
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
        return Commands.getProjectById(projectId);
    }

    @PostMapping("/{projectId}/bid")
    public BidInfo bidProject(@PathVariable("projectId") String projectId, @RequestBody BidAmount bidAmount) {
        Project project = Commands.getProjectById(projectId);
        return Commands.addBid(project, bidAmount.getBidAmount());
    }

    @GetMapping("/{projectId}/bid")
    public boolean isUserBid(@PathVariable("projectId") String projectId){
        Project project = Commands.getProjectById(projectId);
        return Commands.userIsBidBefore(project , Commands.getDefaultUser());
    }
}
