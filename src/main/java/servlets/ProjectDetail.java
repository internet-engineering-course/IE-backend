package servlets;

import client.models.HttpResponse;
import command.Commands;
import database.impl.MemoryDataBase;
import entities.Project;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.StringTokenizer;

@WebServlet(name = "ProjectDetail" , value = "/project/*")
public class ProjectDetail extends HttpServlet {
    @Override
    public void init() throws ServletException {
        MemoryDataBase.getInstance().initialize();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringTokenizer tokenizer = new StringTokenizer(request.getPathInfo(), "/");
        String id = tokenizer.nextToken();

        Project project = Commands.getProjectById(id);

        if(Commands.hasEnoughSkills(Commands.getDefaultUser() , project))
        {
            request.setAttribute("project", project);
            response.setContentType("text/html; charset=UTF-8");
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/projectDetail.jsp");
            requestDispatcher.forward(request, response);
        }else{
            //request.setAttribute("project", project);
            //            response.setContentType("text/html; charset=UTF-8");
            //            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/projectDetail.jsp");
            //            requestDispatcher.forward(request, response);
            response.setStatus(403);
        }
    }

}
