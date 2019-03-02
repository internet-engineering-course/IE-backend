package servlets;

import command.Commands;
import database.impl.MemoryDataBase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Endorse", value = "/endorse")
public class EndorseServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        MemoryDataBase.getInstance().initialize();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer endorserId = Commands.getDefaultUser().getId();
        Integer endorsedId = Integer.valueOf(req.getParameter("endorsedId"));
        String skillName = req.getParameter("skillName");
        Commands.endorseSkill(endorserId, endorsedId, skillName);
        resp.sendRedirect("/user/" + endorsedId);
    }
}
