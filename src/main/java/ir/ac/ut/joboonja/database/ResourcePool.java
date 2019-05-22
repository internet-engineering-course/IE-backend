package ir.ac.ut.joboonja.database;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ResourcePool {
    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:mysql://localhost:8889/IE");
        ds.setUsername("navid");
        ds.setPassword("0021390568");
        ds.setMinIdle(100);
        ds.setMaxIdle(1000);
        ds.setMaxOpenPreparedStatements(1000);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private ResourcePool(){ }
}
