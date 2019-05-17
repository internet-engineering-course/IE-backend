package ir.ac.ut.joboonja.repositories.impl;

import ir.ac.ut.joboonja.database.ResourcePool;
import ir.ac.ut.joboonja.exceptions.BadRequestException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

abstract class JDBCRepository<E> {

    private final static int SQLITE_CONSTRAINT_UNIQUE = 19;

    abstract String getTableName();
    abstract E toDomainModel(ResultSet resultSet) throws SQLException;

    List<E> merge(List<E> rawResult) {
        return rawResult;
    }

    private List<E> execQuery(String query) {
        LinkedList<E> result = new LinkedList<>();
        try {
            Connection connection = ResourcePool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                result.add(toDomainModel(resultSet));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return result;
    }

    void execUpdate(String sql) {
        try {
            Connection connection = ResourcePool.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    List<E> findAll(String query) {
        return merge(execQuery(query));
    }

    E findOne(String query) {
        List<E> result = execQuery(query);
        if (result.isEmpty())
            return null;
        return merge(result).get(0);
    }

    boolean exists(String query) {
        boolean res;
        try {
            Connection connection = ResourcePool.getConnection();
            Statement statement = connection.createStatement();
            res = statement.executeQuery(query).getBoolean("result");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BadRequestException("Something is wrong in db: " + e.getMessage());
        }
        return res;
    }

    private void handleSQLException(SQLException e) {
        if (e.getErrorCode() == SQLITE_CONSTRAINT_UNIQUE)
            throw new BadRequestException("Username already exists!");
        e.printStackTrace();
        throw new BadRequestException("Something is wrong in db: " + e.getMessage());
    }
}
