package ir.ac.ut.joboonja.repositories.impl;

import ir.ac.ut.joboonja.database.ResourcePool;
import ir.ac.ut.joboonja.entities.Skill;
import ir.ac.ut.joboonja.repositories.SkillRepository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SkillRepositoryImpl implements SkillRepository {
    @Override
    public boolean skillExists(Skill skill) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = ResourcePool.getConnection();
        Statement statement = connection.createStatement();
        boolean res = statement.execute(String.format("select * where exists (select *from Skill s where s.name ='%s')", skill.getName()));
        statement.close();
        connection.close();
        return res;
    }

    @Override
    public void insertSkill(Skill skill) throws ClassNotFoundException, SQLException {
        String sql = String.format("INSERT INTO Skill(name) SELECT '%s' WHERE NOT EXISTS(SELECT * FROM Skill WHERE name = '%s');", skill.getName(), skill.getName());
        Class.forName("org.sqlite.JDBC");
        Connection connection = ResourcePool.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
        connection.close();
    }

    @Override
    public Skill getSkill(String skillName) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = ResourcePool.getConnection();
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery(String.format("select * from Skill s where s.name = '%s'", skillName));
        String name = res.getString("name");
        Integer point = 0;
        Skill skill = new Skill(name , point);
        statement.close();
        connection.close();
        return skill;
    }

    @Override
    public List<Skill> getAllSkills() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = ResourcePool.getConnection();
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("select * from Skill s");
        List<Skill> skills= new LinkedList<>();
        while(res.next()){
            String name = res.getString("name");
            Integer point = 0;
            Skill skill = new Skill(name , point);
            skills.add(skill);
        }
        statement.close();
        connection.close();
        return skills;
    }
}
