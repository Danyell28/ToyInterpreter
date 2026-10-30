package interpreter.model.stmt.fileStatements;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.AssignStmt;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CloseRFileStmtTest {
    @TempDir
    Path dir;   // must not be private
    private PrgState state;

    @BeforeEach
    void setUp() throws ADTException {
        state = PrgState.of(new NopStmt());
    }

    @Test
    void expressionDoesNotReturnStringException(){
        IStmt stmt = new CloseRFileStmt(new ValueExp( new IntValue(67)));
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("closeRFile: 67 is not a string", ex.getMessage());
    }

    @Test
    void fileNameIsNotOpenedException(){
        IStmt stmt = new CloseRFileStmt(new ValueExp( new StringValue("in.txt")));
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("closeRFile: \"in.txt\" is not opened", ex.getMessage());
    }


    @Test
    void failureWhileClosingException(){

        BufferedReader failing = new BufferedReader(new StringReader("")) {
            @Override
            public void close() throws IOException {
                throw new IOException("disk on fire");
            }
        };
        StringValue name = new StringValue("missing.txt");
        state.getFileTable().put(name, failing);

        IStmt stmt = new CloseRFileStmt(new ValueExp(name));
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));

        assertTrue(ex.getMessage().contains("disk on fire"));
        assertTrue(state.getFileTable().isDefined(name));
    }

    @Test
    void closingAnOpenFileClosesTheReaderAndRemovesItFromTheTable() throws Exception {
        Path file = dir.resolve("data.txt");
        Files.writeString(file, "hello\n");
        StringValue name = new StringValue(file.toString());
        BufferedReader reader = new BufferedReader(new FileReader(file.toFile()));
        state.getFileTable().put(name, reader);

        new CloseRFileStmt(new ValueExp(name)).execute(state);

        assertFalse(state.getFileTable().isDefined(name));
        assertThrows(IOException.class, reader::readLine);
    }
}