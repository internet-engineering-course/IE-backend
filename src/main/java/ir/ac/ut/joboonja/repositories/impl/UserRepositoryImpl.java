package ir.ac.ut.joboonja.repositories.impl;

import ir.ac.ut.joboonja.database.PreparedQuery;
import ir.ac.ut.joboonja.entities.Skill;
import ir.ac.ut.joboonja.entities.User;
import ir.ac.ut.joboonja.repositories.UserRepository;
import ir.ac.ut.joboonja.services.UserService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

public class UserRepositoryImpl extends JDBCRepository<User> implements UserRepository {
    @Override
    public void insertUser(User user) {
        String sql = String.format("insert into %s (username,firstname,lastname,password,jobTitle,bio,imageUrl) values ( ?,?,?,?,?,?,? )", getTableName());
        List<Object> params = Arrays.asList(user.getUsername(), user.getFirstname(), user.getLastname(), user.getPassword(), user.getJobTitle(), user.getBio(), user.getImageUrl());
        execUpdate(new PreparedQuery(sql, params));
    }

    @Override
    public void addUserSkill(Integer userId, String skillName) {
        String sql = "INSERT INTO UserSkill (userId, skillName, points) VALUES (?, ?, 0);";
        List<Object> params = Arrays.asList(userId, skillName);
        execUpdate(new PreparedQuery(sql, params));
    }

    @Override
    public void deleteUserSkill(Integer userId, String skillName) {
        String sql = "DELETE FROM UserSkill WHERE userId = ? AND skillName = ?;";
        List<Object> params = Arrays.asList(userId, skillName);
        execUpdate(new PreparedQuery(sql, params));
    }

    @Override
    public User getUser(String username) {
        String query = String.format("SELECT * FROM %s u " +
                "LEFT JOIN UserSkill us on u.id = us.userId " +
                "WHERE u.username = ?;",
            getTableName());
        List<Object> params = Collections.singletonList(username);
        return findOne(new PreparedQuery(query, params));
    }

    @Override
    public User getUserById(Integer id) {
        String query = String.format("SELECT * FROM %s u " +
                "LEFT JOIN UserSkill us on u.id = us.userId " +
                "WHERE u.id = ?;",
            getTableName());
        List<Object> params = Collections.singletonList(id);
        return findOne(new PreparedQuery(query, params));
    }

    @Override
    public void updateUserSkillPoint(Integer userId, String skillName, Integer points) {
        String sql = "UPDATE UserSkill SET points = points + ? WHERE userId = ? AND  skillName = ?;";
        List<Object> params = Arrays.asList(points, userId, skillName);
        execUpdate(new PreparedQuery(sql, params));
    }

    @Override
    public List<User> getAllUsers() {
        String query = String.format("SELECT * FROM %s u " +
                "LEFT JOIN UserSkill us on u.id = us.userId;",
            getTableName());
        return findAll(new PreparedQuery(query, Collections.emptyList()));
    }

    @Override
    public List<User> searchUsers(String filter, User user) {
        String query = "SELECT * FROM User u " +
                "LEFT JOIN UserSkill us ON u.id = us.userId " +
                "WHERE ( u.firstname LIKE ? or u.lastname LIKE ? ) and u.id <> ?;";
        List<Object> params = Arrays.asList("%"+filter+"%", "%"+filter+"%", user.getId());
        return findAll(new PreparedQuery(query, params));
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
            resultSet.getString("password"),
            resultSet.getString("imageUrl"),
            skills
        );
    }

    @Override
    List<User> merge(List<User> rawResult) {
        Map<Integer, List<User>> users = rawResult.stream().collect(groupingBy(User::getId));
        LinkedList<User> result = new LinkedList<>();
        for (Integer userId: users.keySet()) {
            LinkedList<Skill> userSkills = new LinkedList<>();
            for (User u: users.get(userId)) {
                Skill skill = u.getSkills().get(0);
                if (skill.getName() != null)
                    userSkills.add(skill);
            }

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
                "\t\tconstraint user_pk\n" +
                "\t\t\tprimary key autoincrement,\n" +
                "\tusername varchar(100) not null,\n" +
                "\tfirstname varchar(100),\n" +
                "\tlastname varchar(100),\n" +
                "\tpassword varchar(100) not null,\n" +
                "\tjobTitle text,\n" +
                "\tbio text,\n" +
                "\timageUrl text\n" +
                ");\n" +
                "\n" +
                "create unique index if not exists user_username_uindex\n" +
                "\ton user (username);\n" +
                "\n"+
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
