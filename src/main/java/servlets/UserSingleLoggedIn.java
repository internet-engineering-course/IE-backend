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
import java.util.List;
import java.util.StringTokenizer;

@WebServlet(name = "UserSingleLoggedIn", value = "/user/*")
public class UserSingleLoggedIn extends HttpServlet {
    @Override
    public void init() throws ServletException {
        MemoryDataBase.getInstance().initialize();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringTokenizer tokenizer = new StringTokenizer(request.getPathInfo(), "/");
        String userId = tokenizer.nextToken();
        User user = Commands.getUserById(Integer.valueOf(userId));
        request.setAttribute("user", user);
        request.setAttribute("skills", Commands.getAllSkills());
        response.setContentType("text/html; charset=UTF-8");
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/user-single-logged-in.jsp");
        requestDispatcher.forward(request, response);
    }
}
