package ir.ac.ut.joboonja.entities;

public class UserSkill {
    private Integer userId;
    private String skillName;
    private Integer points;

    public UserSkill(Integer userId, String skillName, Integer points) {
        this.userId = userId;
        this.skillName = skillName;
        this.points = points;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public static String getCreateScript() {
        return "create table if not exists UserSkill (\n" +
            "  userId integer,\n" +
            "  skillName varchar (100),\n" +
            "  points integer,\n" +
            "  primary key (userId, skillName)\n" +
            ")";
    }
}
