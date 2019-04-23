package ir.ac.ut.joboonja.entities;

public class Skill {
    private String name;
    private Integer point;

    public Skill() {}

    public Skill(String name, Integer point) {
        this.name = name;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Skill) {
            Skill skill = (Skill)obj;
            return name.equals(skill.name);
        }
        else {
            return false;
        }
    }

    public static String getCreateScript(){
        return "create table if not exists Skill\n" +
                "(\n" +
                "\tname varchar(100),\n" +
                "\tid integer\n" +
                "\t\tconstraint Skill_pk\n" +
                "\t\t\tprimary key autoincrement\n" +
                ");\n" +
                "\n" +
                "create unique index if not exists Skill_name_uindex\n" +
                "\ton Skill (name);\n" +
                "\n";
    }
}
