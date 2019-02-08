package entities;

import java.util.List;

public class Project {

    private String title;
    private Integer budget;
    private List<Skill> skills;

    public Project() {}

    public Project(String title, Integer budget, List<Skill> skills) {
        this.title = title;
        this.budget = budget;
        this.skills = skills;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
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

    public List<Skill> getSkills() {
        return skills;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Project) {
            Project project = (Project)obj;
            return title.equals(project.title);
        }
        else {
            return false;
        }
    }
}
