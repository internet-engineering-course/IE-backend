package ir.ac.ut.joboonja.entities;

public class ProjectSkill {
    private String projectId;
    private String skillName;
    private Integer point;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public ProjectSkill(String projectId, String skillName, int point) {
        this.projectId = projectId;
        this.skillName = skillName;
        this.point = point;
    }

    public static String getCreateScript(){
        return "create table if not exists ProjectSkill\n" +
                "(\n" +
                "\tprojectId varchar(36)\n" +
                "\t\tconstraint ProjectSkill_Project_id_fk\n" +
                "\t\t\treferences Project\n" +
                "\t\t\t\ton update cascade on delete cascade,\n" +
                "\tskillName varchar(100)\n" +
                "\t\tconstraint ProjectSkill_Skill_name_fk\n" +
                "\t\t\treferences Skill (name)\n" +
                "\t\t\t\ton update cascade on delete cascade,\n" +
                "\tpoint int,\n" +
                "primary key(projectId,skillName)"+
                ");";
    }
}
