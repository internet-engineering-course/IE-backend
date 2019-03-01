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
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "project")
public class Project extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("initializing.....");
        MemoryDataBase.getInstance().initialize();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<entities.Project> valideProjects = Commands.getValidProjects(Commands.getDefaultUser());
        System.out.println(valideProjects.size());
        request.setAttribute("projects", valideProjects);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/project.jsp");
        requestDispatcher.forward(request, response);
    }
}
