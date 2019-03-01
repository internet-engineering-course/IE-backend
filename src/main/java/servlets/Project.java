package servlets;

import command.Commands;
import database.impl.MemoryDataBase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/project" , name = "project")
public class Project extends HttpServlet {
    @Override
    public void init() throws ServletException {
        MemoryDataBase.getInstance().initialize();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<entities.Project> validProjects = Commands.getValidProjects(Commands.getDefaultUser());
        request.setAttribute("projects", validProjects);
        response.setContentType("text/html; charset=UTF-8");
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/project.jsp");
        requestDispatcher.forward(request, response);
    }
}
