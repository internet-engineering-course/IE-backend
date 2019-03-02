package servlets;

import command.Commands;
import entities.Project;
import entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BidServlet" , value = "/bid")
public class BidServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = Commands.getDefaultUser();
        Project project = Commands.getProjectById(request.getParameter("projectId").toString());
        Integer bidAmount = Integer.parseInt(request.getParameter("bidAmount"));
        Commands.addBid(project , user , bidAmount);
        response.sendRedirect("/project/" + request.getParameter("projectId"));
    }
}
