package entities;

public class Endorse {
    private Integer endorserId;
    private Integer endorsedId;
    private String skillName;

    public Endorse(Integer endorserId, Integer endorsedId, String skillName) {
        this.endorserId = endorserId;
        this.endorsedId = endorsedId;
        this.skillName = skillName;
    }

    public Integer getEndorserId() {
        return endorserId;
    }

    public void setEndorserId(Integer endorserId) {
        this.endorserId = endorserId;
    }

    public Integer getEndorsedId() {
        return endorsedId;
    }

    public void setEndorsedId(Integer endorsedId) {
        this.endorsedId = endorsedId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
}
