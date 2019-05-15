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
        @RequestParam(name = "pageSize", required = false) Integer pageSize,
        @RequestAttribute("user") User user
    ) {
        if (pageNumber != null && pageSize != null)
            return ProjectService.getValidProjects(user, pageNumber, pageSize);
        return ProjectService.getValidProjects(user);
    }

    @GetMapping("/{projectId}")
    public Project getProject(@PathVariable("projectId") String projectId, @RequestAttribute("user") User user) {
        return ProjectService.getProjectById(projectId, user);
    }

    @PostMapping("/{projectId}/bid")
    public Bid bidProject(@PathVariable("projectId") String projectId, @RequestBody BidAmount bidAmount, @RequestAttribute("user") User user) {
        Project project = ProjectService.getProjectById(projectId, user);
        return AuctionService.bidProject(project, bidAmount.getBidAmount(), user);
    }

    @GetMapping("/{projectId}/bid")
    public BidAmount isUserBid(@PathVariable("projectId") String projectId, @RequestAttribute("user") User user) {
        Project project = ProjectService.getProjectById(projectId, user);
        return AuctionService.hasUserBid(project, user);
    }

    @GetMapping("/{projectId}/auction")
    public User projectWinner(@PathVariable("projectId") String projectId, @RequestAttribute("user") User user){
        Project project = ProjectService.getProjectById(projectId, user);
        return AuctionService.holdAuction(project);
    }

    @GetMapping("/search")
    public List<Project> searchProjects(
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestAttribute("user") User user) {
        if (filter == null || filter.isEmpty())
            return Collections.emptyList();

        return ProjectService.searchProjectsPaginated(filter, pageNumber, pageSize, user);
    }
}
