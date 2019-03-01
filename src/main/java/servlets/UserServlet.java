package servlets;

import command.Commands;
import database.impl.MemoryDataBase;
import entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.StringTokenizer;

@WebServlet(name = "User", value = "/user/*")
public class UserServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        MemoryDataBase.getInstance().initialize();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringTokenizer tokenizer = new StringTokenizer(request.getPathInfo(), "/");
        Integer userId = Integer.valueOf(tokenizer.nextToken());
        User user = Commands.getUserById(userId);
        User defaultUser = Commands.getDefaultUser();
        if (user.equals(defaultUser)) {
            request.setAttribute("user", user);
            request.setAttribute("skills", Commands.getAllSkills());
            response.setContentType("text/html; charset=UTF-8");
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/user-single-logged-in.jsp");
            requestDispatcher.forward(request, response);
        } else {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/user-single-guest.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
