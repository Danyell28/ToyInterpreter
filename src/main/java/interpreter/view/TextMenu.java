package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;

import java.util.Scanner;

public class TextMenu {
    private final Scanner scanner = new Scanner(System.in);
    private IStmt selected = null;

    public void run(){
        while(true){
            printMenu();
            int choice = scanner.nextInt();
            switch( choice ){
                case 1: selectProgram(); break;
                case 2: runSelected(); break;
                case 0: System.out.println("Bye bye!!!"); return ;
                default: System.out.println("Invalid input!");
            }
        }
    }

    void printMenu(){
        System.out.println();
        System.out.println("1. Select one of the programs");
        System.out.println("2. Execute the selected program");
        System.out.println("0. Exit ");
        System.out.print("> ");
    }

    void selectProgram(){
        while(true){
            System.out.println();
            for(int i=0; i<4; i++){
                System.out.println(i+1 + ". "+Examples.ALL.get(i));
            }
            System.out.print("> ");
            int choice = scanner.nextInt();
            switch(choice){
                case 1: selected = Examples.ALL.get(0); return;
                case 2: selected = Examples.ALL.get(1); return;
                case 3: selected = Examples.ALL.get(2); return;
                case 4: selected = Examples.ALL.get(3); return;
                default: System.out.println("Invalid input!!!");
            }
        }
    }

    void runSelected(){
        if( selected == null ){
            System.out.println("Select a program first!!!");
            return;
        }
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), selected);
        IRepository repo = new Repository(state);
        Controller controller = new Controller( repo, true );
        try{
            controller.allStep();
        }
        catch(MyException ex){
            System.out.println("Error: " +ex.getMessage());
        }
    }



}
