package ir.ac.ut.joboonja.controllers;

import ir.ac.ut.joboonja.entities.Project;
import ir.ac.ut.joboonja.entities.User;
import ir.ac.ut.joboonja.models.BidAmount;
import ir.ac.ut.joboonja.entities.Bid;
import ir.ac.ut.joboonja.services.AuctionService;
import ir.ac.ut.joboonja.services.ProjectService;
import ir.ac.ut.joboonja.services.UserService;
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
            return ProjectService.getValidProjects(UserService.getDefaultUser(), pageNumber, pageSize);
        return ProjectService.getValidProjects(UserService.getDefaultUser());
    }

    @GetMapping("/{projectId}")
    public Project getProject(@PathVariable("projectId") String projectId) {
        return ProjectService.getProjectById(projectId);
    }

    @PostMapping("/{projectId}/bid")
    public Bid bidProject(@PathVariable("projectId") String projectId, @RequestBody BidAmount bidAmount) {
        Project project = ProjectService.getProjectById(projectId);
        return AuctionService.bidProject(project, bidAmount.getBidAmount());
    }

    @GetMapping("/{projectId}/bid")
    public BidAmount isUserBid(@PathVariable("projectId") String projectId){
        Project project = ProjectService.getProjectById(projectId);
        return AuctionService.hasUserBid(project , UserService.getDefaultUser());
    }

    @GetMapping("/{projectId}/auction")
    public User projectWinner(@PathVariable("projectId") String projectId){
        Project project = ProjectService.getProjectById(projectId);
        return AuctionService.holdAuction(project);
    }

    @GetMapping("/search")
    public List<Project> searchProjects(
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        if (filter == null || filter.isEmpty())
            return Collections.emptyList();

        return ProjectService.searchProjectsPaginated(filter, pageNumber, pageSize);
    }
}
