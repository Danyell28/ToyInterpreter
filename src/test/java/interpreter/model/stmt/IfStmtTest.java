package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws ADTException {
        state = PrgState.of(new NopStmt());
    }

    @Test
    void conditionNotBooleanException(){
        IStmt statement = new IfStmt(new ValueExp(new IntValue(5)), new NopStmt(), new NopStmt());
        var ex = assertThrows(StatementException.class, () -> statement.execute(state));
        assertEquals("Condition is not a boolean", ex.getMessage());
    }

    @Test
    void conditionIsMetSoPushesFirstStatement() throws MyException {
        IStmt first = new AssignStmt("x", new ValueExp(new IntValue(15)));
        IStmt second = new NopStmt();

        IStmt statement = new IfStmt( new ValueExp(new BoolValue(true)),  first, second);
        statement.execute(state);
        assertEquals(state.getExeStack().pop(), first);
    }

    @Test
    void conditionIsNotMetSoPushesSecondStatement() throws MyException {
        IStmt first = new AssignStmt("x", new ValueExp(new IntValue(15)));
        IStmt second = new NopStmt();

        IStmt statement = new IfStmt( new ValueExp(new BoolValue(false)),  first, second);
        statement.execute(state);
        assertEquals(state.getExeStack().pop(), second);
    }
}