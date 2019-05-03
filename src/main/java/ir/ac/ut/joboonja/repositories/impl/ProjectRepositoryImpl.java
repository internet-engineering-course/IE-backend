package ir.ac.ut.joboonja.repositories.impl;

import ir.ac.ut.joboonja.entities.Project;
import ir.ac.ut.joboonja.entities.Skill;
import ir.ac.ut.joboonja.entities.User;
import ir.ac.ut.joboonja.repositories.ProjectRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class ProjectRepositoryImpl extends JDBCRepository<Project> implements ProjectRepository {

    @Override
    public List<Project> getAllProjects(User user) {
        String query = String.format("SELECT * " +
                "FROM %s p JOIN ProjectSkill ps on p.id = ps.projectId " +
                "where not exists " +
                    "(select * " +
                    "from ProjectSkill pss " +
                    "where pss.projectId = p.id and not exists " +
                        "(select * " +
                        "from User u ,UserSkill us " +
                        "where u.id = %d and us.userId = u.id and us.skillName = pss.skillName and us.points >= pss.point)) ORDER BY creationDate DESC;", getTableName(), user.getId());
        return findAll(query);
    }

    @Override
    public void insertProject(Project project) {
        String sql = String.format("insert or ignore into %s (id,title,description,imageUrl,budget,deadline,creationDate) values ('%s','%s','%s','%s',%d,%d,%d )",
                getTableName(), project.getId(), project.getTitle(), project.getDescription(), project.getImageUrl(), project.getBudget(), project.getDeadline(), project.getCreationDate());
        execUpdate(sql);

        List<Skill> skills = project.getSkills();
        for(Skill skill:skills){
            sql = String.format("insert or ignore into ProjectSkill(projectId,skillName,point) values('%s','%s',%d)", project.getId(), skill.getName(), skill.getPoint());
            execUpdate(sql);
        }
    }

    @Override
    public Project getProjectById(String id) {
        String query = String.format("SELECT * FROM %s p " +
            "JOIN ProjectSkill ps on p.id = ps.projectId " +
            "WHERE p.id = '%s';",
            getTableName(), id);
        return findOne(query);
    }

    @Override
    public List<Project> searchProjects(String filter) {
        String query = String.format("SELECT * FROM %s p " +
            "JOIN ProjectSkill ps ON p.id = ps.projectId " +
            "WHERE title LIKE \"%%%s%%\" or description LIKE \"%%%s%%\" ORDER BY creationDate DESC;", getTableName(), filter ,filter);
        return findAll(query);
    }

    @Override
    public List<Project> getProjectsPaginated(User user, Integer pageNumber, Integer pageSize) {
        String query = String.format("SELECT * " +
                "FROM %s p JOIN ProjectSkill ps on p.id = ps.projectId " +
                "where not exists " +
                "(select * " +
                "from ProjectSkill pss " +
                "where pss.projectId = p.id and not exists " +
                "(select * " +
                "from User u ,UserSkill us " +
                "where u.id = %d and us.userId = u.id and us.skillName = pss.skillName and us.points >= pss.point)) ORDER BY creationDate DESC LIMIT %d OFFSET %d;", getTableName(), user.getId(), pageSize, pageNumber*pageSize);
        return findAll(query);
    }

    @Override
    String getTableName() {
        return "Project";
    }

    @Override
    Project toDomainModel(ResultSet resultSet) throws SQLException {
        List<Skill> skills = Collections.singletonList(
            new Skill(resultSet.getString("skillName"), resultSet.getInt("point"))
        );
        return new Project(
            resultSet.getString("id"),
            resultSet.getString("title"),
            resultSet.getString("description"),
            resultSet.getString("imageUrl"),
            resultSet.getInt("budget"),
            resultSet.getLong("deadline"),
            resultSet.getLong("creationDate"),
            skills
        );
    }

    @Override
    List<Project> merge(List<Project> rawResult) {
        Map<String, List<Project>> projects = rawResult.stream().collect(groupingBy(Project::getId, LinkedHashMap::new, Collectors.toList()));
        LinkedList<Project> result = new LinkedList<>();
        for (String projectId: projects.keySet()) {
            LinkedList<Skill> projectSkills = new LinkedList<>();
            for (Project p: projects.get(projectId))
                projectSkills.add(p.getSkills().get(0));

            Project project = projects.get(projectId).get(0);
            project.setSkills(projectSkills);
            result.add(project);
        }
        return result;
    }

    public static String getCreateScript() {
        return
            "create table if not exists Project\n" +
                "(\n" +
                "\tid char(36)\n" +
                "\t\tconstraint Project_pk\n" +
                "\t\t\tprimary key,\n" +
                "\ttitle varchar(240),\n" +
                "\tdescription text,\n" +
                "\timageURL text,\n" +
                "\tbudget integer,\n" +
                "\tdeadline integer,\n" +
                "\tcreationDate integer\n" +
                ");\n" +
                "\n" +
                "create table if not exists ProjectSkill\n" +
                "(\n" +
                "\tprojectId varchar(36)\n" +
                "\t\tconstraint ProjectSkill_Project_id_fk\n" +
                "\t\t\treferences Project\n" +
                "\t\t\t\ton update cascade on delete cascade,\n" +
                "\tskillName varchar(100)\n" +
                "\t\tconstraint ProjectSkill_Skill_name_fk\n" +
                "\t\t\treferences Skill (name)\n" +
                "\t\t\t\ton update cascade on delete cascade,\n" +
                "\tpoint int,\n" +
                "primary key(projectId,skillName)"+
                ");";
    }
}
