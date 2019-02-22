package httpServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import command.Commands;
import database.impl.MemoryDataBase;
import entities.Project;
import entities.User;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ProjectHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //show project that user can satisfy
        List<Project> valideProjects = Commands.getValidProjects(Commands.getDefaultUser());
        String response ="\n" +
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
            response +="        <tr>\n" +
                    "            <td>"+valideProjects.get(i).getId()+"</td>\n" +
                    "            <td>"+valideProjects.get(i).getTitle()+"</td>\n" +
                    "            <td>"+valideProjects.get(i).getBudget()+"</td>\n" +
                    "        </tr>\n";
        }
        response +="    </table>\n" +
                "</body>\n" +
                "</html>";
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
