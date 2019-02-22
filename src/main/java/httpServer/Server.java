package httpServer;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class Server {
    public void startServer() throws Exception{
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/project", new ProjectHandler());
        server.createContext("/user/", new UserHandler());
        server.createContext("/project/", new ProjectDetailHandler());

        server.setExecutor(null);
        server.start();
    }
}
