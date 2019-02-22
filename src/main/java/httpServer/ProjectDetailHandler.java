package httpServer;

import client.models.HttpResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import command.Commands;
import entities.Project;

import java.io.IOException;
import java.io.OutputStream;
import java.util.StringTokenizer;

public class ProjectDetailHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(exchange.getRequestURI().getPath(), "/");
        String context = tokenizer.nextToken();
        String id = tokenizer.nextToken();
        Project project = Commands.getProjectById(id);
        HttpResponse response = new HttpResponse(null , null);

        if(Commands.hasEnoughSkills(Commands.getDefaultUser() , project))
        {
            response.setResponse("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Project</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <ul>\n" +
                    "        <li>id:"+project.getId()+"</li>\n" +
                    "        <li>title:"+ project.getTitle()+"</li>\n" +
                    "        <li>description:" + project.getDescription()+"</li>\n" +
                    "        <li>imageUrl: <img src=\""+project.getImageUrl()+"\" style=\"width: 50px; height: 50px;\"></li>\n" +
                    "        <li>budget:"+project.getBudget()+"</li>\n" +
                    "    </ul>\n" +
                    "</body>\n" +
                    "</html>"
            );
            response.setResponseCode(200);
        }else{
            response.setResponse("User doesn't meet requirement");
            response.setResponseCode(403);
        }
        exchange.sendResponseHeaders(response.getResponseCode(), response.getResponse().getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getResponse().getBytes());
        os.close();
    }
}
