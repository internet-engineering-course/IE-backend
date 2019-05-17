package ir.ac.ut.joboonja.repositories.impl;

import ir.ac.ut.joboonja.database.PreparedQuery;
import ir.ac.ut.joboonja.entities.Endorse;
import ir.ac.ut.joboonja.repositories.EndorseRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EndorseRepositoryImpl extends JDBCRepository<Endorse> implements EndorseRepository {
    @Override
    public void insertEndorse(Endorse endorse) {
        String sql = String.format("INSERT INTO %s " +
                "(endorserId, endorsedId, skillName) " +
                "VALUES (?, ?, ?);",
            getTableName());
        List<Object> params = Arrays.asList(endorse.getEndorserId(), endorse.getEndorsedId(), endorse.getSkillName());
        execUpdate(new PreparedQuery(sql, params));
    }

    @Override
    public boolean endorseExists(Endorse endorse) {
        String query = String.format("SELECT " +
            "EXISTS(SELECT * FROM %s WHERE endorserId = ? AND endorsedId = ? AND skillName = ?) as result;",
            getTableName());
        List<Object> params = Arrays.asList(endorse.getEndorserId(), endorse.getEndorsedId(), endorse.getSkillName());
        return exists(new PreparedQuery(query, params));
    }

    @Override
    public List<Endorse> getEndorses(Integer endorserId) {
        return findAll(new PreparedQuery("SELECT * FROM " + getTableName(), Collections.emptyList()));
    }

    @Override
    String getTableName() {
        return "Endorse";
    }

    @Override
    Endorse toDomainModel(ResultSet resultSet) throws SQLException {
        return new Endorse(
            resultSet.getInt("endorserId"),
            resultSet.getInt("endorsedId"),
            resultSet.getString("skillName")
        );
    }

    public  static  String getCreateScript(){
        return "CREATE TABLE IF NOT EXISTS Endorse\n" +
            "(\n" +
            "  endorserId INTEGER,\n" +
            "  endorsedId INTEGER,\n" +
            "  skillName  TEXT,\n" +
            "  FOREIGN KEY(endorserId) REFERENCES User(id),\n" +
            "  FOREIGN KEY(endorsedId) REFERENCES User(id),\n" +
            "  FOREIGN KEY(skillName) REFERENCES Skill(skillName)\n" +
            ");";
    }
}
