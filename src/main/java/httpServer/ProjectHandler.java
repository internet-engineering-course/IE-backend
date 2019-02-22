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
    }
}
