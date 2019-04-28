package ir.ac.ut.joboonja.repositories.impl;

import ir.ac.ut.joboonja.entities.Endorse;
import ir.ac.ut.joboonja.repositories.EndorseRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EndorseRepositoryImpl extends JDBCRepository<Endorse> implements EndorseRepository {
    @Override
    public void insertEndorse(Endorse endorse) {
        String sql = String.format("INSERT INTO %s " +
                "(endorserId, endorsedId, skillName) " +
                "VALUES (%d, %d ,'%s');",
            getTableName(), endorse.getEndorserId(), endorse.getEndorsedId(), endorse.getSkillName());
        execUpdate(sql);
    }

    @Override
    public boolean endorseExists(Endorse endorse) {
        String query = String.format("SELECT " +
            "EXISTS(SELECT * FROM %s WHERE endorserId = %d AND endorsedId = %d AND skillName = '%s') as result;",
            getTableName(), endorse.getEndorserId(), endorse.getEndorsedId(), endorse.getSkillName());
        return exists(query);
    }

    @Override
    public List<Endorse> getEndorses(Integer endorserId) {
        return findAll("SELECT * FROM " + getTableName());
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
}
