package ir.ac.ut.joboonja.entities;

public class ProjectSkill {
    private String projectId;
    private String skillName;

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

    public ProjectSkill(String projectId, String skillName) {
        this.projectId = projectId;
        this.skillName = skillName;
    }

    public static String getCreateScript(){
        return "create table if not exists ProjectSkill\n" +
                "(\n" +
                "\tprojectId text\n" +
                "\t\tconstraint ProjectSkill_Project_id_fk\n" +
                "\t\t\treferences Project\n" +
                "\t\t\t\ton update cascade on delete cascade,\n" +
                "\tskillName text\n" +
                "\t\tconstraint ProjectSkill_pk\n" +
                "\t\t\tprimary key\n" +
                "\t\tconstraint ProjectSkill_Skill_name_fk\n" +
                "\t\t\treferences Skill (name)\n" +
                "\t\t\t\ton update cascade on delete cascade\n" +
                ");\n" +
                "\n";
    }
}
