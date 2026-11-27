package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ForkStmtTest {

    @Test
    void checkCorrectStructureForChild() throws MyException {
        PrgState state = PrgState.of(new NopStmt());
        Optional<PrgState> child = new ForkStmt(new NopStmt()).execute(state);
        assertFalse(state.getSymTable() == child.get().getSymTable());
        assertTrue(state.getOut() == child.get().getOut());
        assertTrue(state.getFileTable() == child.get().getFileTable());
        assertTrue(state.getHeap() == child.get().getHeap());
    }
}