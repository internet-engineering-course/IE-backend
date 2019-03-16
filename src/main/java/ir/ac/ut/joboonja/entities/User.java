package ir.ac.ut.joboonja.entities;

import java.util.LinkedList;

public class User {
    private Integer id;
    private String username;
    private String firstname;
    private String lastname;
    private String jobTitle;
    private String bio;
    private LinkedList<Skill> skills;

    public User() {}

    public User(Integer id, String username, String firstname, String lastname, String jobTitle, String bio, LinkedList<Skill> skills) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.jobTitle = jobTitle;
        this.bio = bio;
        this.skills = skills;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LinkedList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(LinkedList<Skill> skills) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}