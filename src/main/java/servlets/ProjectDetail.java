package servlets;

import command.Commands;
import entities.Project;
import entities.User;
import utilities.Serializer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.StringTokenizer;

@WebServlet(name = "ProjectDetail" , value = "/project/*")
public class ProjectDetail extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringTokenizer tokenizer = new StringTokenizer(request.getPathInfo(), "/");
        String id = tokenizer.nextToken();
        User user = Commands.getDefaultUser();
        Project project = Commands.getProjectById(id);
        if (project == null) {
            response.setContentType("text/plain; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().append("Project not found!");
        }
        else if (Commands.hasEnoughSkills(user, project)) {
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().append(Serializer.serialize(project));
        }
        else {
            response.setContentType("text/plain; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().append("Access to project is forbidden!");
        }
    }

}
