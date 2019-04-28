package ir.ac.ut.joboonja.repositories;

import ir.ac.ut.joboonja.entities.Skill;

import java.sql.SQLException;
import java.util.List;

public interface SkillRepository {
    boolean skillExists(Skill skill);
    void insertSkill(Skill skill);
    List<Skill> getAllSkills();
}
