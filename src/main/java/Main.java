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
                    Commands.register(json);
                    break;
                case "addProject":
                    Commands.addProject(json);
                    break;
                default:
                    System.err.println("Command not supported!");
                    break;
            }
        }
    }

}