package ir.ac.ut.joboonja.repositories.impl;

import ir.ac.ut.joboonja.database.ResourcePool;
import ir.ac.ut.joboonja.entities.Project;
import ir.ac.ut.joboonja.repositories.ProjectRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ProjectRepositoryImple implements ProjectRepository {
    @Override
    public List<Project> getAllProjects() {
        return null;
    }

    @Override
    public boolean projectExists(Project project) {
        return false;
    }

    @Override
    public void insertProject(Project project) throws ClassNotFoundException, SQLException {
//        String sql = String.format("INSERT INTO Proejct(id,title,description,imageUrl,budget,deadline,creationDate) SELECT '%s','%s','%s','%s','%i','%i','%i' WHERE NOT EXISTS(SELECT * FROM Skill WHERE id = '%s');"
//                ,project.getId(),project.getTitle(),project.getDescription(),project.getImageUrl(),project.getBudget(),project.getDeadline(),100);
        String sql = "insert or ignore into Project(id,title,description,imageUrl,budget,deadline,creationDate) values ('"+ project.getId() + "','"+ project.getTitle() + "','"+ project.getDescription() + "','"+ project.getImageUrl() + "',"+ project.getBudget() + ","+ project.getDeadline() + ","+ project.getCreationDate() +" )";
        Class.forName("org.sqlite.JDBC");

        Connection connection = ResourcePool.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
        connection.close();
    }

    @Override
    public Project getProject(String projectTitle) {
        return null;
    }

    @Override
    public Project getProjectById(String id) {
        return null;
    }
}
