package ir.ac.ut.joboonja.repositories.impl;

import ir.ac.ut.joboonja.database.PreparedQuery;
import ir.ac.ut.joboonja.entities.Skill;
import ir.ac.ut.joboonja.repositories.SkillRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SkillRepositoryImpl extends JDBCRepository<Skill> implements SkillRepository {
    @Override
    public boolean skillExists(Skill skill) {
        String query = String.format("select exists " +
            "(select * from %s s where s.name = ?) as result", getTableName());
        List<Object> params = Collections.singletonList(skill.getName());
        return exists(new PreparedQuery(query, params));
    }

    @Override
    public void insertSkill(Skill skill) {
        String sql = String.format("INSERT INTO %s(name) " +
            "SELECT ? WHERE NOT EXISTS(SELECT * FROM Skill WHERE name = ?);",
            getTableName());
        List<Object> params = Arrays.asList(skill.getName(), skill.getName());
        execUpdate(new PreparedQuery(sql, params));
    }

    @Override
    public List<Skill> getAllSkills() {
        return findAll(new PreparedQuery("SELECT * FROM " + getTableName(), Collections.emptyList()));
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

    public static String getCreateScript(){
        return "create table if not exists Skill\n" +
                "(\n" +
                "\tname varchar(100) null,\n" +
                "\tconstraint Skill_pk\n" +
                "\t\tprimary key (name)\n" +
                ");";
    }
}
