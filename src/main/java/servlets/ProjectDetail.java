package servlets;

import client.models.HttpResponse;
import command.Commands;
import database.impl.MemoryDataBase;
import entities.Project;
import entities.User;

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringTokenizer tokenizer = new StringTokenizer(request.getPathInfo(), "/");
        String id = tokenizer.nextToken();
        User  user = Commands.getDefaultUser();
        Project project = Commands.getProjectById(id);
        boolean isBidBefore = false;
        if(Commands.hasEnoughSkills(Commands.getDefaultUser() , project))
        {
            request.setAttribute("project", project);
            if(Commands.userIsBidBefore(project , user)){
                isBidBefore = true;
                Integer amount = Commands.getUserBidAmount(project , user);
                request.setAttribute("amount" , amount);
            }
            request.setAttribute("isBidBefore" , isBidBefore);
            response.setContentType("text/html; charset=UTF-8");
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/projectDetail.jsp");
            requestDispatcher.forward(request, response);

        }else{
            response.setStatus(403);
        }
    }

}
