package httpServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class ProjectHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //show project that user can satisfy
    }
}
