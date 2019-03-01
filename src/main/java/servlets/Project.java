package servlets;

import command.Commands;
import database.impl.MemoryDataBase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "project")
public class Project extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("initializing.....");
        MemoryDataBase.getInstance().initialize();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<entities.Project> valideProjects = Commands.getValidProjects(Commands.getDefaultUser());
        String r ="\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Projects</title>\n" +
                "    <style>\n" +
                "        table {\n" +
                "            text-align: center;\n" +
                "            margin: 0 auto;\n" +
                "        }\n" +
                "        td {\n" +
                "            min-width: 300px;\n" +
                "            margin: 5px 5px 5px 5px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <table>\n" +
                "        <tr>\n" +
                "            <th>id</th>\n" +
                "            <th>title</th>\n" +
                "            <th>budget</th>\n" +
                "        </tr>\n";
        for(int i= 0 ; i < valideProjects.size() ; i++) {
            r +="        <tr>\n" +
                    "            <td>"+valideProjects.get(i).getId()+"</td>\n" +
                    "            <td>"+valideProjects.get(i).getTitle()+"</td>\n" +
                    "            <td>"+valideProjects.get(i).getBudget()+"</td>\n" +
                    "        </tr>\n";
        }
        r +="    </table>\n" +
                "</body>\n" +
                "</html>";
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(r);
    }
}
