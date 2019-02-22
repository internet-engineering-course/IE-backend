import database.impl.MemoryDataBase;
import httpServer.Server;

public class Main {

    public static void main(String [] args){
        MemoryDataBase.getInstance().initialize();
        Server server = new Server();
        try {

            server.startServer();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}