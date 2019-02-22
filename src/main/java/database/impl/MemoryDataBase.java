package database.impl;

import client.HttpClient;
import entities.Auction;
import entities.Project;
import entities.User;

import java.util.LinkedList;
import java.util.List;

class MemoryDataBase {
    private List <User> users;
    private List <Project> projects;
    private List <Auction> auctions;

    private MemoryDataBase() {
        this.users = new LinkedList<User>();
        this.projects = new LinkedList<Project>();
        this.auctions = new LinkedList<Auction>();
    }

    private static MemoryDataBase dataBase;
    static MemoryDataBase getInstance() {
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


    Auction getAuction(String auctionTitle) {
        for (Auction auction : auctions) {
            if (auction.getProjectTitle().equals(auctionTitle)) {
                return auction;
            }
        }
        return null;
    }

    public void initialize() {
        System.out.println("initializing memory database ...");
        List<Project> projects = HttpClient.fetchAllProjects();
        for (Project project: projects) {
            insertProject(project);
        }
        System.out.println("fetched all projects ...");
    }
}
