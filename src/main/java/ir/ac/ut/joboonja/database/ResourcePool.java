package ir.ac.ut.joboonja.database;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ResourcePool {
    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:sqlite:sample.db");
        ds.setMinIdle(5);
        ds.setMaxIdle(100);
        ds.setMaxOpenPreparedStatements(1000);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private ResourcePool(){ }
}
