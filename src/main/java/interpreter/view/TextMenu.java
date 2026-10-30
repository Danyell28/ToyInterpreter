package interpreter.view;

import interpreter.view.commands.Command;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private final Map<String, Command> commands = new LinkedHashMap<>();

    public void addCommand(Command c){
        commands.put(c.getKey(), c);
    }

    private void printMenu() {
        System.out.println();
        for (Command c : commands.values())
            System.out.printf("%4s : %s%n", c.getKey(), c.getDescription());
        System.out.println();
    }

    public void show(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.println("Input the option: ");
            Command c = commands.get(scanner.nextLine().strip());
            if ( c == null ){
                System.out.println("Invalid option!");
                continue;
            }
            c.execute();
        }
    }
}
