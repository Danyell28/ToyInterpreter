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
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.Reader;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CloseRFileStmtTest {
    @TempDir
    Path dir;   // must not be private
    private PrgState state;

    @BeforeEach
    void setUp() throws ADTException {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new AssignStmt("dummy", new VarExp("dummy")));
        state.getExeStack().pop();
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

    /*
    @Test
    void closingAnExistentFileException(){
        state.getFileTable().put(new StringValue("missing.txt"), )
        IStmt stmt = new OpenRFileStmt(new ValueExp(new StringValue("missing.txt")));
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertTrue(ex.getMessage().startsWith("openRFile: cannot open "));
        assertFalse(state.getFileTable().isDefined(new StringValue("missing.txt")));
    }*/
}