import command.Commands;
import entities.User;
import exceptions.DeserializeException;
import httpServer.Server;

import java.util.Scanner;

public class Main {

    public static void main(String [] args){

        Server server = new Server();
        try {

            server.startServer();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}