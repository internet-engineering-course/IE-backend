package ir.ac.ut.joboonja.repositories.impl;

import ir.ac.ut.joboonja.database.ResourcePool;
import ir.ac.ut.joboonja.entities.Skill;
import ir.ac.ut.joboonja.exceptions.BadRequestException;
import ir.ac.ut.joboonja.repositories.SkillRepository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SkillRepositoryImpl extends JDBCRepository<Skill> implements SkillRepository {
    @Override
    public boolean skillExists(Skill skill) {
        boolean res;
        try {
            Connection connection = ResourcePool.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("select exists " +
                "(select * from %s s where s.name = '%s') as result", getTableName(), skill.getName());
            res = statement.executeQuery(query).getBoolean("result");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BadRequestException("Something is wrong in db: " + e.getMessage());
        }
        return res;
    }

    @Override
    public void insertSkill(Skill skill) {
        String sql = String.format("INSERT INTO %s(name) " +
            "SELECT '%s' WHERE NOT EXISTS(SELECT * FROM Skill WHERE name = '%s');",
            getTableName(), skill.getName(), skill.getName());
        execUpdate(sql);
    }

    @Override
    public List<Skill> getAllSkills() {
        return findAll("SELECT * FROM " + getTableName());
    }

    public static String getCreateScript(){
        return "create table if not exists Skill\n" +
                "(\n" +
                "\tname text\n" +
                "\t\tconstraint Skill_pk\n" +
                "\t\t\tprimary key\n" +
                ");";
    }

    @Override
    String getTableName() {
        return "Skill";
    }

    @Override
    Skill toDomainModel(ResultSet resultSet) throws SQLException {
        return new Skill(
            resultSet.getString("name"),
            null
        );
    }
}
