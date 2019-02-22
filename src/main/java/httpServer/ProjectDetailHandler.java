package httpServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.StringTokenizer;

public class ProjectDetailHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(exchange.getRequestURI().getPath(), "/");
        String context = tokenizer.nextToken();
        String id = tokenizer.nextToken();
        System.out.println(id);
    }
}
