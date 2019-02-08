package entities;

import java.util.List;

public class User {
    private String username;
    private List <Skill> skills;

    public User() {}

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User user = (User)obj;
            return username.equals(user.username);
        }
        else {
            return false;
        }
    }
}
