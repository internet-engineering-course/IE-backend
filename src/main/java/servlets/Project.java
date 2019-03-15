package servlets;

import command.Commands;
import utilities.Serializer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/project" , name = "project")
public class Project extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<entities.Project> validProjects = Commands.getValidProjects(Commands.getDefaultUser());
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().append(Serializer.serialize(validProjects));
    }
}
