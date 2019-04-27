package ir.ac.ut.joboonja.entities;

import java.util.List;

public class Project {

    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private Integer budget;
    private Long deadline;
    private List<Skill> skills;
    private Long creationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    public Project(String id, String title, String description, String imageUrl, Integer budget, Long deadline, Long creationDate, List<Skill> skills) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.budget = budget;
        this.deadline = deadline;
        this.skills = skills;
        this.creationDate = creationDate;
    }

    public Project() {}




    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Project) {
            Project project = (Project)obj;
            return id.equals(project.id);
        }
        else {
            return false;
        }
    }

}
