package ir.ac.ut.joboonja.repositories.impl;

import ir.ac.ut.joboonja.database.PreparedQuery;
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

    private PreparedQuery generateQuery(
        Integer userId, String filter, Integer pageNumber, Integer pageSize, String projectId
    ) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(userId);
        String like = "";
        if (filter != null) {
            like = "where proj.title LIKE ? or proj.description LIKE ?";
            params.add("%"+filter+"%");
            params.add("%"+filter+"%");
        }

        String where = "";
        if (projectId != null) {
            where = "WHERE proj.id = ?";
            params.add(projectId);
        }

        String limit = "";
        if (pageNumber != null && pageSize != null) {
            limit = "LIMIT ?, ?";
            params.add(pageNumber*pageSize);
            params.add(pageSize);
        }

        String query =
            "select * FROM (SELECT * FROM (SELECT * FROM Project p where not exists\n" +
            "(select * from ProjectSkill ps where ps.projectId = p.id and not exists\n" +
            "(select * from User u, UserSkill us\n" +
            "where u.id = ? and us.userId = u.id and us.skillName = ps.skillName and us.points >= ps.point)\n" +
            ")) as proj " + like + where + " ORDER BY proj.creationDate DESC " + limit + ") as result join ProjectSkill on result.id = projectId;\n";
        return new PreparedQuery(query, params);
    }

    @Override
    public List<Project> getAllProjects(User user) {
        return findAll(generateQuery(user.getId(), null, null, null, null));
    }

    @Override
    public List<Project> getAllProjects() {
        String query = "select * from Project p, ProjectSkill ps where ps.projectId = p.id";
        return findAll(new PreparedQuery(query, Collections.emptyList()));
    }

    @Override
    public void insertProject(Project project) {
        String sql = String.format("insert or ignore into %s (id,title,description,imageUrl,budget,deadline,creationDate) values ( ?,?,?,?,?,?,? )", getTableName());
        List<Object> params = Arrays.asList(project.getId(), project.getTitle(), project.getDescription(), project.getImageUrl(), project.getBudget(), project.getDeadline(), project.getCreationDate());
        execUpdate(new PreparedQuery(sql, params));

        List<Skill> skills = project.getSkills();
        for(Skill skill: skills){
            sql = "insert or ignore into ProjectSkill(projectId,skillName,point) values(?,?,?)";
            params = Arrays.asList(project.getId(), skill.getName(), skill.getPoint());
            execUpdate(new PreparedQuery(sql, params));
        }
    }

    @Override
    public Project getProjectById(String id, User user) {
        return findOne(generateQuery(user.getId(), null, null, null, id));
    }

    @Override
    public List<Project> searchProjects(String filter, User user) {
        return findAll(generateQuery(user.getId(), filter, null, null, null));
    }

    @Override
    public List<Project> searchProjectsPaginated(String filter, Integer pageNumber, Integer pageSize, User user) {
        return findAll(generateQuery(user.getId(), filter, pageNumber, pageSize, null));
    }

    @Override
    public List<Project> getProjectsPaginated(User user, Integer pageNumber, Integer pageSize) {
        return findAll(generateQuery(user.getId(), null, pageNumber, pageSize, null));
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

    public static String getCreateProjectSkillScript(){
        return  "create table if not exists ProjectSkill\n" +
                "(\n" +
                "\tprojectId varchar(36) not null,\n" +
                "\tskillName varchar(100) not null,\n" +
                "\tpoint integer null,\n" +
                "\tconstraint ProjectSkill_pk\n" +
                "\t\tprimary key (projectId, skillName),\n" +
                "\tconstraint ProjectSkill_Project_id_fk\n" +
                "\t\tforeign key (projectId) references Project (id)\n" +
                "\t\t\ton update cascade on delete cascade,\n" +
                "\tconstraint ProjectSkill_Skill_name_fk\n" +
                "\t\tforeign key (skillName) references Skill (name)\n" +
                "\t\t\ton update cascade on delete cascade\n" +
                ");\n" +
                "\n";
    }

    public static String getCreateScript() {
        return "create table if not exists Project\n" +
                "(\n" +
                "\tid varchar(36) not null,\n" +
                "\ttitle varchar(100) null,\n" +
                "\tdescription text null,\n" +
                "\timageUrl text null,\n" +
                "\tbudget integer null,\n" +
                "\tdeadline integer null,\n" +
                "\tcreationDate integer null,\n" +
                "\tconstraint Project_pk\n" +
                "\t\tprimary key (id)\n" +
                ");\n" +
                "\n";
    }
}
