package ir.ac.ut.joboonja.entities;

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Endorse) {
            Endorse endorse = (Endorse)obj;
            return endorsedId.equals(endorse.getEndorsedId()) &&
                endorserId.equals(endorse.getEndorserId()) &&
                skillName.equals(endorse.getSkillName());
        }
        else {
            return false;
        }
    }

    public  static  String getCreateScript(){
        return "create table if not exists Endorse\n" +
                "(\n" +
                "\tendorserId integer\n" +
                "\t\tconstraint Endorse_User_id_fk\n" +
                "\t\t\treferences User\n" +
                "\t\t\t\ton update cascade on delete cascade,\n" +
                "\tendorsedId integer\n" +
                "\t\tconstraint Endorse_User_id_fk_2\n" +
                "\t\t\treferences User\n" +
                "\t\t\t\ton update cascade on delete cascade,\n" +
                "\tskillName varchar(100)\n" +
                "\t\tconstraint Endorse_pk\n" +
                "\t\t\tprimary key\n" +
                "\t\tconstraint Endorse_Skill_name_fk\n" +
                "\t\t\treferences Skill (name)\n" +
                "\t\t\t\ton update cascade on delete cascade\n" +
                ");\n";
    }
}
