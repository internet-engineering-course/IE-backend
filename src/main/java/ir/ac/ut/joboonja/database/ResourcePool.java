package ir.ac.ut.joboonja.database;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ResourcePool {
    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:mysql://database:3306/IE?useUnicode=yes&characterEncoding=UTF-8");
        ds.setUsername("root");
        ds.setPassword("mypassword");
        ds.setMinIdle(100);
        ds.setMaxIdle(1000);
        ds.setMaxOpenPreparedStatements(1000);
    }

    private static void coolDown(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static Connection getConnection() {
        Connection connection;
        while (true) {
            try {
                connection = ds.getConnection();
                break;
            } catch (SQLException e) {
                System.out.println("Unable to get db connection retrying in 5 seconds ...");
                coolDown(5000);
            }
        }
        return connection;
    }

    private ResourcePool(){ }
}
