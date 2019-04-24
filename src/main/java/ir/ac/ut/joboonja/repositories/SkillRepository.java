package ir.ac.ut.joboonja.repositories;

import ir.ac.ut.joboonja.entities.Skill;

import java.sql.SQLException;
import java.util.List;

public interface SkillRepository {
    boolean skillExists(Skill skill) throws ClassNotFoundException, SQLException;
    void insertSkill(Skill skill) throws ClassNotFoundException, SQLException;
    Skill getSkill(String skillName) throws ClassNotFoundException, SQLException;
    List<Skill> getAllSkills() throws SQLException, ClassNotFoundException;
}
