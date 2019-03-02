package servlets;

import command.Commands;
import entities.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.StringTokenizer;

@WebServlet(name = "UserSkill", value = "/skill/*")
public class UserSkill extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringTokenizer tokenizer = new StringTokenizer(req.getPathInfo(), "/");
        String method = tokenizer.nextToken();
        User user = Commands.getDefaultUser();
        if (method.equals("delete")) {
            Commands.deleteUserSkill(user.getId(), req.getParameter("skillName"));
        }
        else if (method.equals("add")) {
            Commands.updateUserSkill(user.getId(), req.getParameter("skillName"));
        }
        resp.sendRedirect("/user/" + user.getId());
    }
}
