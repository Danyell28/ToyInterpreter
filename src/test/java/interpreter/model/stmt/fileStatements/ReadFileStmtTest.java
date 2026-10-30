package interpreter.model.stmt.fileStatements;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.IExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.AssignStmt;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ReadFileStmtTest {
    @TempDir
    Path dir;   // must not be private
    private PrgState state;

    @BeforeEach
    void setUp() throws ADTException {
        state = PrgState.of(new NopStmt());
    }

    private void fakeFile(String name, BufferedReader reader) {
        state.getFileTable().put(new StringValue(name), reader);
    }

    private BufferedReader readerOf(String content) {
        return new BufferedReader(new StringReader(content));
    }

    @Test
    void variableIsNotDeclaredException() {
        IStmt stmt = new ReadFileStmt(new ValueExp(new StringValue("in.txt")), "x");
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("Variable x is not declared", ex.getMessage());
    }

    @Test
    void variableIsDeclaredButNotIntegerException() throws MyException {
        new VarDeclStmt("x", new BoolType()).execute(state);
        IStmt stmt = new ReadFileStmt(new ValueExp(new StringValue("in.txt")), "x");
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("Variable x is not an integer", ex.getMessage());
    }

    @Test
    void expressionIsNotAStringException() throws MyException {
        new VarDeclStmt("x", new IntType()).execute(state);
        IStmt stmt = new ReadFileStmt(new ValueExp(new IntValue(67)), "x");
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("readFile: 67 is not a string", ex.getMessage());
    }

    @Test
    void fileIsNotOpenException() throws MyException {
        new VarDeclStmt("x", new IntType()).execute(state);
        IStmt stmt = new ReadFileStmt(new ValueExp(new StringValue("in.txt")), "x");
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("readFile: \"in.txt\" is not opened", ex.getMessage());
    }

    @Test
    void nonIntegerLineThrows() throws MyException {
        new VarDeclStmt("x", new IntType()).execute(state);
        fakeFile("in.txt", readerOf("abc\n"));
        IStmt stmt = new ReadFileStmt(new ValueExp(new StringValue("in.txt")), "x");

        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));

        assertTrue(ex.getMessage().startsWith("readFile: could not read from "));
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));   // unchanged
    }

    @Test
    void ioErrorWhileReadingThrows() throws MyException {
        new VarDeclStmt("x", new IntType()).execute(state);
        BufferedReader failing = new BufferedReader(new StringReader("")) {
            @Override
            public String readLine() throws IOException {
                throw new IOException("disk on fire");
            }
        };
        fakeFile("in.txt", failing);
        IStmt stmt = new ReadFileStmt(new ValueExp(new StringValue("in.txt")), "x");
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertTrue(ex.getMessage().contains("disk on fire"));
    }

    @Test
    void readGivesTheNumbersInOrder() throws MyException {
        new VarDeclStmt("x", new IntType()).execute(state);
        fakeFile("in.txt", readerOf("15\n50\n"));
        IStmt stmt = new ReadFileStmt(new ValueExp(new StringValue("in.txt")), "x");

        stmt.execute(state);
        assertEquals(new IntValue(15), state.getSymTable().lookup("x"));
        stmt.execute(state);
        assertEquals(new IntValue(50), state.getSymTable().lookup("x"));
        stmt.execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }


}