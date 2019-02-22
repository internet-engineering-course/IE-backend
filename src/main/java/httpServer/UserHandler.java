package httpServer;

import client.models.HttpResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import command.Commands;
import entities.User;

import java.io.IOException;
import java.io.OutputStream;
import java.util.StringTokenizer;

public class UserHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(exchange.getRequestURI().getPath(), "/");
        String context = tokenizer.nextToken();
        String id = tokenizer.nextToken();
        User user = Commands.getUserById(Integer.valueOf(id));
        HttpResponse response = new HttpResponse(null, 0);
        if (user != null) {
            response.setResponse(
                "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "    <meta charset=\"UTF-8\">" +
                    "    <title>User</title>" +
                    "</head>" +
                    "<body>" +
                    "    <ul>" +
                    "        <li>id: " + user.getId() + "</li>" +
                    "        <li>first name: " + user.getFirstname() + "</li>" +
                    "        <li>last name: " + user.getLastname() + "</li>" +
                    "        <li>jobTitle: " + user.getJobTitle() + "</li>" +
                    "        <li>bio: " + user.getBio() + "</li>" +
                    "    </ul>" +
                    "</body>" +
                    "</html>");
            response.setResponseCode(200);
        } else {
            response.setResponse("User not found!");
            response.setResponseCode(404);
        }
        exchange.sendResponseHeaders(response.getResponseCode(), response.getResponse().getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getResponse().getBytes());
        os.close();
    }
}
