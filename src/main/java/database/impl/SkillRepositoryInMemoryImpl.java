package database.impl;

import database.SkillRepository;
import entities.Skill;

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
