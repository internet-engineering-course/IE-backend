package entities;

import java.util.List;

public class Project {

    private String title;
    private Integer budget;
    private List<Skill> requiredSkills;

    public Project(String title, Integer budget, List<Skill> requiredSkills) {
        this.title = title;
        this.budget = budget;
        this.requiredSkills = requiredSkills;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public void setRequiredSkills(List<Skill> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Integer getBudget() {
        return budget;
    }

    public List<Skill> getRequiredSkills() {
        return requiredSkills;
    }
}
