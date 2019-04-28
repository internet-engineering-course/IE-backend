package ir.ac.ut.joboonja.repositories.impl;

import ir.ac.ut.joboonja.entities.Skill;
import ir.ac.ut.joboonja.entities.User;
import ir.ac.ut.joboonja.repositories.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class UserRepositoryImpl extends JDBCRepository<User> implements UserRepository {
    @Override
    public void insertUser(User user) {
        String sql = String.format("insert into %s (username,firstname,lastname,jobTitle,bio) values ( '%s','%s','%s','%s','%s' )",
            getTableName(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getJobTitle(), user.getBio());
        execUpdate(sql);
    }

    @Override
    public void addUserSkill(Integer userId, String skillName) {
        String sql = String.format("INSERT INTO UserSkill (userId, skillName, points) VALUES (%d, '%s', 0);",
            userId, skillName);
        execUpdate(sql);
    }

    @Override
    public void deleteUserSkill(Integer userId, String skillName) {
        String sql = String.format("DELETE FROM UserSkill WHERE userId = %d AND skillName = '%s';",
            userId, skillName);
        execUpdate(sql);
    }

    @Override
    public User getUser(String username) {
        String query = String.format("SELECT * FROM %s u " +
                "JOIN UserSkill us on u.id = us.userId " +
                "WHERE u.username = '%s';",
            getTableName(), username);
        return findOne(query);
    }

    @Override
    public User getUserById(Integer id) {
        String query = String.format("SELECT * FROM %s u " +
                "JOIN UserSkill us on u.id = us.userId " +
                "WHERE userId = %d;",
            getTableName(), id);
        return findOne(query);
    }

    @Override
    public void updateUserSkillPoint(Integer userId, String skillName, Integer points) {
        String sql = String.format("UPDATE UserSkill SET points = points + %d WHERE userId = %d AND  skillName = '%s';",
            points, userId, skillName);
        execUpdate(sql);
    }

    @Override
    public List<User> getAllUsers() {
        String query = String.format("SELECT * FROM %s u " +
                "JOIN UserSkill us on u.id = us.userId;",
            getTableName());
        return findAll(query);
    }

    @Override
    public List<User> searchUsers(String filter) {
        String query = String.format("SELECT * FROM %s u " +
                "JOIN UserSkill us ON u.id = us.userId " +
                "WHERE printf('%%s%%s%%s', username, firstname, lastname) LIKE \"%%%s%%\";",
            getTableName(), filter);
        return findAll(query);
    }

    @Override
    String getTableName() {
        return "User";
    }

    @Override
    User toDomainModel(ResultSet resultSet) throws SQLException {
        LinkedList<Skill> skills = new LinkedList<>();
        skills.add(new Skill(resultSet.getString("skillName"), resultSet.getInt("points")));
        return new User(
            resultSet.getInt("id"),
            resultSet.getString("username"),
            resultSet.getString("firstname"),
            resultSet.getString("lastname"),
            resultSet.getString("jobTitle"),
            resultSet.getString("bio"),
            skills
        );
    }

    @Override
    List<User> merge(List<User> rawResult) {
        Map<Integer, List<User>> users = rawResult.stream().collect(groupingBy(User::getId));
        LinkedList<User> result = new LinkedList<>();
        for (Integer userId: users.keySet()) {
            LinkedList<Skill> userSkills = new LinkedList<>();
            for (User u: users.get(userId))
                userSkills.add(u.getSkills().get(0));

            User user = users.get(userId).get(0);
            user.setSkills(userSkills);
            result.add(user);
        }
        return result;
    }

    public static String getCreateScript(){
        return "create table if not exists User\n" +
            "(\n" +
            "\tid integer\n" +
            "\t\tconstraint User_pk\n" +
            "\t\t\tprimary key autoincrement,\n" +
            "\tusername varchar(100),\n" +
            "\tfirstname varchar(100),\n" +
            "\tlastname varchar(100),\n" +
            "\tjobTitle text,\n" +
            "\tbio text\n" +
            ");\n" +
            "\n" +
            "create unique index if not exists User_username_uindex\n" +
            "\ton User (username);\n" +
            "\n" +
            "create table if not exists UserSkill\n" +
            "(\n" +
            "\tuserId integer\n" +
            "\t\tconstraint UserSkill_User_id_fk\n" +
            "\t\t\treferences User\n" +
            "\t\t\t\ton update cascade on delete cascade,\n" +
            "\tskillName varchar(100)\n" +
            "\t\tconstraint UserSkill_Skill_id_fk\n" +
            "\t\t\treferences Skill\n" +
            "\t\t\t\ton update cascade on delete cascade,\n" +
            "\tpoints integer,\n" +
            "\tprimary key (userId, skillName)\n" +
            ");";
    }
}
