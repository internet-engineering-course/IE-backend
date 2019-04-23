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
        return "create table if not exists UserSkill\n" +
                "(\n" +
                "\tuserId integer\n" +
                "\t\tconstraint UserSkill_User_id_fk\n" +
                "\t\t\treferences User\n" +
                "\t\t\t\ton update cascade on delete cascade,\n" +
                "\tskillName varchar(100)\n" +
                "\t\tconstraint UserSkill_Skill_id_fk\n" +
                "\t\t\treferences Skill\n" +
                "\t\t\t\ton update cascade on delete cascade,\n" +
                "\tpoints integer,\n" +
                "\tprimary key (userId, skillName)\n" +
                ");";
    }
}
