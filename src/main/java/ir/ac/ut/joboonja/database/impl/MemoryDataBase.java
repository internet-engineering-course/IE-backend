package ir.ac.ut.joboonja.database.impl;

import ir.ac.ut.joboonja.client.HttpClient;
import ir.ac.ut.joboonja.entities.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MemoryDataBase {
    private List <User> users;
    private List <Project> projects;
    private List <Auction> auctions;
    private List<Skill> skills;
    private List<Endorse> endorses;
    private Boolean initialized = false;

    private MemoryDataBase() {
        this.users = new LinkedList<>();
        this.projects = new LinkedList<>();
        this.auctions = new LinkedList<>();
        this.skills = new LinkedList<>();
        this.endorses = new LinkedList<>();
    }

    private static MemoryDataBase dataBase;
    public static MemoryDataBase getInstance() {
        if (dataBase == null)
            dataBase = new MemoryDataBase();
        return dataBase;
    }

    boolean userExists(User user) {
        return users.indexOf(user) != -1;
    }

    void insertUser(User user) {
        if (userExists(user)){
            System.err.println("user already exists.");
            return;
        }
        users.add(user);
    }

    User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    User getUser(Integer id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    boolean projectExists(Project project) {
        return projects.indexOf(project) != -1;
    }

    void insertProject(Project project) {
        if (projectExists(project)) {
            System.err.println("Project already exists.");
            return;
        }
        projects.add(project);
    }

    Project getProject(String projectTitle) {
        for (Project project : projects) {
            if (project.getTitle().equals(projectTitle)) {
                return project;
            }
        }
        return null;
    }

    boolean auctionExists(Auction auction) {
        return auctions.indexOf(auction) != -1;
    }

    void insertAuction(Auction auction) {
        if (auctionExists(auction))
            return;
        auctions.add(auction);
    }

    Auction getAuction(String id) {
        for (Auction auction : auctions) {
            if (auction.getId().equals(id)) {
                return auction;
            }
        }
        return null;
    }

    boolean skillExists(Skill skill) {
        return skills.indexOf(skill) != -1;
    }

    void insertSkill(Skill skill) {
        if (skillExists(skill)){
            System.err.println("skill already exists.");
            return;
        }
        skills.add(skill);
    }

    Skill getSkill(String skillName) {
        for (Skill skill : skills) {
            if (skill.getName().equals(skillName)) {
                return skill;
            }
        }
        return null;
    }

    List<Skill> getAllSkills() {
        return skills;
    }

    void insertEndorse(Endorse endorse) {
        endorses.add(endorse);
    }

    List<Endorse> getEndorses(Integer endorserId) {
        List<Endorse> result = new LinkedList<>();
        for(Endorse e: endorses)
            if(e.getEndorserId().equals(endorserId))
                result.add(e);
        return result;
    }

    boolean endorseExists(Endorse endorse) {
        return endorses.indexOf(endorse) != -1;
    }

    public void initialize() {
        if(initialized)
            return;

        System.out.println("initializing memory ir.ac.ut.joboonja.database ...");

        User user = new User(1 , "ali" , "علی" , "شریف زاده" , "برنامه نویس وب",
                "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلیکارا بکنه ولی پول نداشت",
            new LinkedList<>(Arrays.asList(
                new Skill("HTML", 5),
                new Skill("Javascript", 4),
                new Skill("C++", 2),
                new Skill("Java", 3)
            ))
        );
        insertUser(user);
        user = new User(2 , "ahmadreza" , "احمدرضا" , "صبور" , "برنامه نویس وب",
            "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلیکارا بکنه ولی پول نداشت",
            new LinkedList<>(Arrays.asList(
                new Skill("SQL", 5),
                new Skill("Django", 4),
                new Skill("Linux", 3),
                new Skill("Java", 3)
            ))
        );
        insertUser(user);
        user = new User(3 , "navid" , "نوید" , "اکبری" , "دانشجو",
                "no bio",
                new LinkedList<>(Arrays.asList(
                        new Skill("SQL", 5),
                        new Skill("Django", 4),
                        new Skill("Linux", 3),
                        new Skill("Python", 3)
                ))
        );
        insertUser(user);

        System.out.println("initialized users ...");
        List<Project> projects = HttpClient.fetchAllProjects();
        for (Project project: projects) {
            insertProject(project);
        }
        System.out.println("fetched all projects ...");

        List<Skill> skills = HttpClient.fetchAllSkills();
        for (Skill skill: skills) {
            insertSkill(skill);
        }
        System.out.println("fetched all skills ...");
        initialized = true;
    }

    List<Project> getAllProjects() {
        return projects;
    }

    Project getProjectById(String id) {
        for (Project project : projects) {
            if (project.getId().equals(id)) {
                return project;
            }
        }
        return null;
    }

    void updateUserSkill(Integer userId, String skillName) {
        User user = getUser(userId);
        Skill skill = getSkill(skillName);
        if (user.getSkills().indexOf(skill) != -1) {
            System.err.println("User " + user.getUsername() + " already has skill " + skill.getName());
            return;
        }
        user.getSkills().add(new Skill(skillName, 0));
    }

    void deleteUserSkill(Integer userId, String skillName) {
        User user = getUser(userId);
        Skill skill = getSkill(skillName);
        int index = user.getSkills().indexOf(skill);
        if (index == -1) {
            System.err.println("User " + user.getUsername() + " doesn't have skill " + skill.getName());
            return;
        }
        user.getSkills().remove(index);
    }
    List<User> getAllUser(){
        return  users;
    }
}
