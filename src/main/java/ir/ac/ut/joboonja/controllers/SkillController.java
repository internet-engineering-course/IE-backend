package ir.ac.ut.joboonja.controllers;

import ir.ac.ut.joboonja.entities.Skill;
import ir.ac.ut.joboonja.services.SkillService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/skill")
public class SkillController {

    @GetMapping
    public List<Skill> getSkills() {
        return SkillService.getAllSkills();
    }
}
