package ir.ac.ut.joboonja.services;

import ir.ac.ut.joboonja.entities.Skill;
import ir.ac.ut.joboonja.repositories.SkillRepository;
import ir.ac.ut.joboonja.repositories.impl.SkillRepositoryImpl;

import java.util.List;

public class SkillService {
    private static SkillRepository skillRepository = new SkillRepositoryImpl();

    public static void insertSkill(Skill skill) {
        skillRepository.insertSkill(skill);
    }

    public static List<Skill> getAllSkills() {
        return skillRepository.getAllSkills();
    }

    public static boolean skillExists(Skill skill) {
        return skillRepository.skillExists(skill);
    }
}
