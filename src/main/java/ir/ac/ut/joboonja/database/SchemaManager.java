package ir.ac.ut.joboonja.database;

import ir.ac.ut.joboonja.entities.UserSkill;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SchemaManager {

    private static List<String> schemaSQLScripts = new ArrayList<>(
        Arrays.asList(
            UserSkill.getCreateScript()
        )
    );

    public static void initialSchema() {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = ResourcePool.getConnection();
            Statement statement = connection.createStatement();
            for(String schema: schemaSQLScripts) {
                statement.executeUpdate(schema);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
