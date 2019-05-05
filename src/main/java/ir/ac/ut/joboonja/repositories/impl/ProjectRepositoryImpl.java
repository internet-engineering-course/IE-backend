package ir.ac.ut.joboonja.repositories.impl;

import ir.ac.ut.joboonja.command.Commands;
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

    private String generateQuery(
        Integer userId, String filter, Integer pageNumber, Integer pageSize, String projectId
    ) {
        String like = "";
        if (filter != null)
            like = "where proj.title LIKE '%"+filter+"%' or proj.description LIKE '%"+filter+"%'";

        String limit = "";
        if (pageNumber != null && pageSize != null)
            limit = String.format("LIMIT %d, %d", pageNumber*pageSize, pageSize);

        String where = "";
        if (projectId != null)
            where = String.format("WHERE proj.id = '%s'", projectId);

        return "select * \n" +
                "FROM (SELECT * FROM (SELECT * FROM Project p where not exists\n" +
                "(select * from ProjectSkill ps where ps.projectId = p.id and not exists\n" +
                "(select * from User u, UserSkill us\n" +
                "where u.id = "+ userId +" and us.userId = u.id and us.skillName = ps.skillName and us.points >= ps.point)\n" +
                ")) as proj " + like + where + " ORDER BY proj.creationDate DESC " + limit + ") as result join ProjectSkill on result.id = projectId;\n";

    }

    @Override
    public List<Project> getAllProjects(User user) {
        String query = generateQuery(user.getId(), null, null, null, null);
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
        User user = Commands.getDefaultUser();
        String query = generateQuery(user.getId(), null, null, null, id);
        return findOne(query);
    }

    @Override
    public List<Project> searchProjects(String filter) {
        User user = Commands.getDefaultUser();
        String query = generateQuery(user.getId(), filter, null, null, null);
        return findAll(query);
    }

    @Override
    public List<Project> searchProjectsPaginated(String filter, Integer pageNumber, Integer pageSize) {
        User user = Commands.getDefaultUser();
        String query = generateQuery(user.getId(), filter, pageNumber, pageSize, null);
        return findAll(query);
    }

    @Override
    public List<Project> getProjectsPaginated(User user, Integer pageNumber, Integer pageSize) {
        String query = generateQuery(user.getId(), null, pageNumber, pageSize, null);
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
