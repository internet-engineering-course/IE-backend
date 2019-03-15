package servlets;

import command.Commands;
import entities.User;
import utilities.Serializer;

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

        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().append(Serializer.serialize(users));
    }
}
