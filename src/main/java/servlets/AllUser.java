package servlets;

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
import java.util.List;

@WebServlet(name = "AllUser" , value = "/alluser")
public class AllUser extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = Commands.getDefaultUser();
        List<User> users = Commands.getAllUsers(user);

        response.setContentType("text/html; charset=UTF-8");
        request.setAttribute("users" , users);
        request.setAttribute("size" , users.size());
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/allUser.jsp");
        requestDispatcher.forward(request, response);
    }
}
