package ir.ac.ut.joboonja.controllers;

import ir.ac.ut.joboonja.command.Commands;
import ir.ac.ut.joboonja.entities.Project;
import ir.ac.ut.joboonja.entities.User;
import ir.ac.ut.joboonja.models.BidAmount;
import ir.ac.ut.joboonja.models.BidInfo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @GetMapping
    public List<Project> getProjects(
        @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
        @RequestParam(name = "pageSize", required = false) Integer pageSize
    ) {
        if (pageNumber != null && pageSize != null)
            return Commands.getValidProjects(Commands.getDefaultUser(), pageNumber, pageSize);
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
    public BidAmount isUserBid(@PathVariable("projectId") String projectId){
        Project project = Commands.getProjectById(projectId);
        return Commands.hasUserBid(project , Commands.getDefaultUser());
    }

    @GetMapping("/{projectId}/auction")
    public User projectWinner(@PathVariable("projectId") String projectId){
        Project project = Commands.getProjectById(projectId);
        return Commands.auction(project);
    }

    @GetMapping("/search")
    public List<Project> searchProjects(@RequestParam(name = "filter", required = false) String filter) {
        if (filter == null || filter.isEmpty())
            return Collections.emptyList();

        return Commands.searchValidProjects(filter);
    }
}
