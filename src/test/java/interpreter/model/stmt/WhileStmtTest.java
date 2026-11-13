package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhileStmtTest {
    private PrgState state;
    @BeforeEach
    void setUp() throws ADTException {
        state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    void conditionNotBoolException(){
        IExp exp = new ValueExp(new IntValue(5));
        var ex = assertThrows(StatementException.class, () -> new WhileStmt(exp, new NopStmt()).execute(state));
        assertEquals("while: " + exp + " is not a boolean", ex.getMessage());
    }

    @Test
    void whilePushesStatementOnTheStack() throws MyException {
        IExp exp1 = new ValueExp(new BoolValue(false));
        IStmt stmt = new NopStmt();
        new WhileStmt(exp1, stmt).execute(state);

        assertTrue(state.getExeStack().isEmpty());

        IExp exp2 = new ValueExp(new BoolValue(true));
        IStmt w = new WhileStmt(exp2, stmt);
        w.execute(state);

        assertEquals(stmt, state.getExeStack().pop());
        assertEquals(w, state.getExeStack().pop());
    }
}