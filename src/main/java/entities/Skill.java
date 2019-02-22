package entities;

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
}
