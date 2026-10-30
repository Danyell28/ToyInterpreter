package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.*;
import interpreter.model.exp.VarExp;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompStmtTest {

    private final IStmt first = new NopStmt();
    private final IStmt second = new NopStmt();
    private final IStmt comp = new CompStmt(first, second);
    private PrgState state;

    @BeforeEach
    void setUp() throws ADTException {
        state = PrgState.of(new NopStmt());
    }

    @Test
    void pushesFirstOnTopOfSecond() throws MyException {
        comp.execute(state);
        assertSame(first, state.getExeStack().pop());
        assertSame(second, state.getExeStack().pop());
    }

}