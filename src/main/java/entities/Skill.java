package entities;

public class Skill {
    private String name;
    private Integer points;

    public Skill() {}

    public Skill(String name, Integer points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
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
