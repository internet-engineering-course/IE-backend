package ir.ac.ut.joboonja.database;

import ir.ac.ut.joboonja.client.HttpClient;
import ir.ac.ut.joboonja.command.Commands;
import ir.ac.ut.joboonja.entities.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SchemaManager {

    private static List<String> schemaSQLScripts = new ArrayList<>(
        Arrays.asList(
                Skill.getCreateScript(),
                User.getCreateScript(),
                UserSkill.getCreateScript(),
                Project.getCreateScript(),
                Endorse.getCreateScript(),
                ProjectSkill.getCreateScript()
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
            List<Skill> skills = HttpClient.fetchAllSkills();
            for(Skill skill:skills) {
                Commands.insertSkill(skill);
            }
            List<Project> projects = HttpClient.fetchAllProjects();
            for(Project project:projects){
                Commands.insertProject(project);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
