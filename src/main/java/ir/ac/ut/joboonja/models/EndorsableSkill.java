package ir.ac.ut.joboonja.models;

import ir.ac.ut.joboonja.entities.Skill;

public class EndorsableSkill {
    private Skill skill;
    private boolean endorsable;

    public EndorsableSkill(Skill skill, Boolean endorsable) {
        this.skill = skill;
        this.endorsable = endorsable;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Boolean getEndorsable() {
        return endorsable;
    }

    public void setEndorsable(boolean endorsable) {
        this.endorsable = endorsable;
    }
}
