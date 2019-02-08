import entities.User;
import exceptions.DeserializeException;

import java.util.Scanner;

public class Main {

    public static void main(String [] args){
        Scanner sc = new Scanner(System.in);
        while (true) {
            String[] input = sc.nextLine().split(" ");
            if (input.length != 2) {
                System.err.println("Command not supported!");
                continue;
            }
            String command = input[0];
            String json = input[1];

            switch (command){
                case "register":
                    try {
                        Commands.register(json);
                    } catch (DeserializeException e) {
                        e.printStackTrace();
                    }
                    break;
                case "addProject":
                    try {
                        Commands.addProject(json);
                    } catch (DeserializeException e) {
                        e.printStackTrace();
                    }
                    break;
                case "bid":
                    try {
                        if(Commands.addBid(json))
                            System.err.println("Bid is successfully added");
                        else
                            System.err.println("The user doesn't meet the requirements");
                    } catch (DeserializeException e) {
                        e.printStackTrace();
                    }
                    break;
                case "auction":
                    User user = null;
                    try {
                        user = Commands.auction(json);
                    } catch (DeserializeException e) {
                        e.printStackTrace();
                    }
                    System.out.print("winner: ");
                    System.out.println(user.getUsername());
                    return;
                default:
                    System.err.println("Command not supported!");
                    break;
            }
        }
    }
}