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
import java.util.StringTokenizer;

@WebServlet(name = "User", value = "/user/*")
public class UserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringTokenizer tokenizer = new StringTokenizer(request.getPathInfo(), "/");
        Integer userId = Integer.valueOf(tokenizer.nextToken());
        User user = Commands.getUserById(userId);
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().append(Serializer.serialize(user));
    }
}
