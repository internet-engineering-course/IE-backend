package ir.ac.ut.joboonja.database;

import ir.ac.ut.joboonja.entities.Skill;

import java.util.List;

public interface SkillRepository {
    boolean skillExists(Skill skill);
    void insertSkill(Skill skill);
    Skill getSkill(String skillName);
    List<Skill> getAllSkills();
}
