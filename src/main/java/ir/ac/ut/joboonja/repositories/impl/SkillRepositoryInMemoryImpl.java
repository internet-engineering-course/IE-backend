package ir.ac.ut.joboonja.repositories.impl;

import ir.ac.ut.joboonja.repositories.SkillRepository;
import ir.ac.ut.joboonja.entities.Skill;

import java.util.List;

public class SkillRepositoryInMemoryImpl implements SkillRepository {

    @Override
    public boolean skillExists(Skill skill) {
        return MemoryDataBase.getInstance().skillExists(skill);
    }

    @Override
    public void insertSkill(Skill skill) {
        MemoryDataBase.getInstance().insertSkill(skill);
    }

    @Override
    public Skill getSkill(String skillName) {
        return MemoryDataBase.getInstance().getSkill(skillName);
    }

    @Override
    public List<Skill> getAllSkills() {
        return MemoryDataBase.getInstance().getAllSkills();
    }
}
