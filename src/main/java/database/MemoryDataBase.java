package database;

import entities.Auction;
import entities.Project;
import entities.User;
import models.BidInfo;

import java.util.LinkedList;
import java.util.List;

public class MemoryDataBase {
    private List <User> users;
    private List <Project> projects;
    private List <Auction> auctions;

    public MemoryDataBase() {
        this.users = new LinkedList<>();
        this.projects = new LinkedList<>();
        this.auctions = new LinkedList<>();
    }

    private static MemoryDataBase dataBase;
    public static MemoryDataBase getInstance() {
        if (dataBase == null)
            dataBase = new MemoryDataBase();
        return dataBase;
    }

    public boolean userExists(User user) {
        return users.indexOf(user) != -1;
    }

    public void insertUser(User user) {
        if (userExists(user)){
            System.err.println("user already exists.");
            return;
        }
        users.add(user);
    }

    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean projectExists(Project project) {
        return projects.indexOf(project) != -1;
    }

    public void insertProject(Project project) {
        if (projectExists(project)) {
            System.err.println("user already exists.");
            return;
        }
        projects.add(project);
    }

    public Project getProject(String projectTitle) {
        for (Project project : projects) {
            if (project.getTitle().equals(projectTitle)) {
                return project;
            }
        }
        return null;
    }

    public boolean auctionExists(Auction auction) {
        return auctions.indexOf(auction) != -1;
    }

    public void insertAuction(Auction auction) {
        if (auctionExists(auction))
            return;
        auctions.add(auction);
    }


    public Auction getAuction(String auctionTitle) {
        for (Auction auction : auctions) {
            if (auction.getProjectTitle().equals(auctionTitle)) {
                return auction;
            }
        }
        return null;
    }
}
