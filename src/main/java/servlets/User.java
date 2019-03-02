package servlets;

import client.models.HttpResponse;
import command.Commands;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.StringTokenizer;

@WebServlet(name = "User" , value = "/user/*")
public class User extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringTokenizer tokenizer = new StringTokenizer(request.getPathInfo(), "/");
        String id = tokenizer.nextToken();
        entities.User user = Commands.getUserById(Integer.valueOf(id));

        if (user != null) {
            response.setContentType("text/html; charset=UTF-8");
            request.setAttribute("user" , user);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/userDetail.jsp");
            requestDispatcher.forward(request, response);

        } else {
            response.setStatus(404);
        }

    }
}
