package ir.ac.ut.joboonja.database;

import ir.ac.ut.joboonja.client.HttpClient;
import ir.ac.ut.joboonja.command.Commands;
import ir.ac.ut.joboonja.entities.*;
import ir.ac.ut.joboonja.repositories.impl.ProjectRepositoryImpl;
import ir.ac.ut.joboonja.repositories.impl.SkillRepositoryImpl;
import ir.ac.ut.joboonja.repositories.impl.UserRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SchemaManager {

    private static List<String> schemaSQLScripts = new ArrayList<>(
        Arrays.asList(
                SkillRepositoryImpl.getCreateScript(),
                UserRepositoryImpl.getCreateScript(),
                ProjectRepositoryImpl.getCreateScript(),
                Endorse.getCreateScript()
        )
    );

    public static void initialSchema() {
        try {
            for(String schema: schemaSQLScripts) {
                Class.forName("org.sqlite.JDBC");
                Connection connection = ResourcePool.getConnection();
                Statement statement = connection.createStatement();
                statement.executeUpdate(schema);
                statement.close();
                connection.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void syncData() {
        List<Skill> skills = HttpClient.fetchAllSkills();
        for(Skill skill:skills) {
            Commands.insertSkill(skill);
        }
        List<Project> projects = HttpClient.fetchAllProjects();
        for(Project project:projects){
            Commands.insertProject(project);
        }
    }
}
