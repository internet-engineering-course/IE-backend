package entities;

import java.util.List;

public class User {
    private String username;
    private List <Skill> skills;

    public User(String username, List<Skill> skills) {
        this.username = username;
        this.skills = skills;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
