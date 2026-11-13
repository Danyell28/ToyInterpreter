package interpreter.controller;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.*;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller controller;
    private IRepository repo;
    private PrgState state;

    @TempDir
    Path dir;

    @BeforeEach
    void setUp() throws ADTException {
        state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
        repo = new Repository(state, dir.resolve("log.txt").toString());
        controller = new Controller(repo, false);
    }

    @Test
    void oneStepOnEmptyStackException() {
        var ex = assertThrows(StatementException.class, () -> controller.oneStep(state));
        assertEquals("program state stack is empty", ex.getMessage());
    }

    @Test
    void oneStepOnCompStmtLeavesItsPartsOnTheStack() throws MyException {
        IStmt decl = new VarDeclStmt("v", new IntType());
        IStmt print = new PrintStmt(new VarExp("v"));
        PrgState s = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),
                new CompStmt(decl, print));

        controller.oneStep(s);
        assertSame(decl, s.getExeStack().pop());
        assertSame(print, s.getExeStack().pop());
        assertTrue(s.getExeStack().isEmpty());
    }


    @Test
    void allStepOnCompStmtWillExecuteEverythingOnTheStack() throws MyException {
        IStmt decl = new VarDeclStmt("v", new IntType());
        IStmt print = new PrintStmt(new VarExp("v"));
        IStmt comp = new CompStmt(decl, print);
        state = PrgState.of(comp);
        repo = new Repository(state, dir.resolve("log.txt").toString());
        controller = new Controller(repo, false);
        controller.allStep();
        assertTrue(state.getExeStack().isEmpty());
    }

    private PrgState run(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        var repo = new Repository(state, dir.resolve("log.txt").toString());
        new Controller(repo, false).allStep(); // display off
        return state;
    }

    @Test
    void fileExampleReadsBothNumbersAndClosesTheFile() throws Exception {
        Path in = dir.resolve("test.in");
        Files.writeString(in, "15\n50\n");
        PrgState s = run(Examples.fileExample(in.toString()));
        assertEquals(new IntValue(15), s.getOut().get(0));
        assertEquals(new IntValue(50), s.getOut().get(1));
        assertTrue(s.getFileTable().toMap().isEmpty());
    }

    @Test
    void logFileContainsTheFourSectionsInOrder() throws Exception {
        run(Examples.EX1);
        String log = Files.readString(dir.resolve("log.txt"));
        int a = log.indexOf("ExeStack:"), b = log.indexOf("SymTable:"),
                c = log.indexOf("Out:"), d = log.indexOf("FileTable:");
        assertTrue(0 <= a && a < b && b < c && c < d);
    }
}