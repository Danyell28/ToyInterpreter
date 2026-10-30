package interpreter.model.stmt.fileStatements;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
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
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class OpenRFileStmtTest {
    @TempDir
    Path dir; // a new empty folder for every test
    private PrgState state;
    private ValueExp file;

    @BeforeEach
    void setUp() throws Exception {
        state = PrgState.of(new NopStmt());
    }

    @Test
    void expressionDoesNotReturnStringException(){
        IStmt stmt = new OpenRFileStmt(new ValueExp( new IntValue(67)));
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("openRFile: 67 is not a string", ex.getMessage());
    }

    @Test
    void fileNameAlreadyOpenedException(){
        state.getFileTable().put(new StringValue("in.txt"), new BufferedReader(Reader.nullReader()));

        IStmt stmt = new OpenRFileStmt(new ValueExp( new StringValue("in.txt")));
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("openRFile: \"in.txt\" is already opened", ex.getMessage());
    }

    @Test
    void openingANonExistentFileException(){
        StringValue missing = new StringValue(dir.resolve("missing.txt").toString());

        IStmt stmt = new OpenRFileStmt(new ValueExp(missing));

        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));

        assertTrue(ex.getMessage().startsWith("openRFile: cannot open "));
        assertFalse(state.getFileTable().isDefined(new StringValue("missing.txt")));
    }

    @Test
    void actuallyOpensFile() throws IOException, MyException {
        Path file = dir.resolve("data.txt");
        Files.writeString(file, "hello\n");
        StringValue name = new StringValue(file.toString());

        new OpenRFileStmt(new ValueExp(name)).execute(state);

        assertTrue(state.getFileTable().isDefined(name));
        BufferedReader reader = state.getFileTable().lookup(name);
        try {
            assertEquals("hello", reader.readLine());
        } finally {
            reader.close();   // otherwise Windows can't delete the temp folder
        }
    }
}