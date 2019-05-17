package ir.ac.ut.joboonja.repositories.impl;

import ir.ac.ut.joboonja.database.PreparedQuery;
import ir.ac.ut.joboonja.database.ResourcePool;
import ir.ac.ut.joboonja.exceptions.BadRequestException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

abstract class JDBCRepository<E> {

    private final static int SQLITE_CONSTRAINT_UNIQUE = 19;

    abstract String getTableName();
    abstract E toDomainModel(ResultSet resultSet) throws SQLException;

    List<E> merge(List<E> rawResult) {
        return rawResult;
    }

    private void fillPreparedStatement(PreparedStatement preparedStatement, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++)
            preparedStatement.setObject(i+1, params.get(i));
    }

    private List<E> execQuery(PreparedQuery query) {
        LinkedList<E> result = new LinkedList<>();
        try {
            Connection connection = ResourcePool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query.getPreparedSql());
            fillPreparedStatement(preparedStatement, query.getParameters());
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                result.add(toDomainModel(resultSet));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return result;
    }

    void execUpdate(PreparedQuery query) {
        try {
            Connection connection = ResourcePool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query.getPreparedSql());
            fillPreparedStatement(preparedStatement, query.getParameters());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    List<E> findAll(PreparedQuery query) {
        return merge(execQuery(query));
    }

    E findOne(PreparedQuery query) {
        List<E> result = execQuery(query);
        if (result.isEmpty())
            return null;
        return merge(result).get(0);
    }

    boolean exists(PreparedQuery query) {
        boolean res;
        try {
            Connection connection = ResourcePool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query.getPreparedSql());
            fillPreparedStatement(preparedStatement, query.getParameters());
            res = preparedStatement.executeQuery().getBoolean("result");
            preparedStatement.close();
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
