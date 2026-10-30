package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrintStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws ADTException {
        state = new PrgState( new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    void printAppendsTheEvaluatedValueToOut() throws MyException {
        new PrintStmt(new ValueExp(new IntValue(5))).execute(state);

        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(5), state.getOut().get(0));
    }

    @Test
    void printEvaluatesVariablesAgainstTheSymTable() throws MyException {
        state.getSymTable().put("x", new IntValue(7));

        new PrintStmt(new VarExp("x")).execute(state);

        assertEquals(new IntValue(7), state.getOut().get(0));
    }

    @Test
    void printKeepsValuesInPrintOrder() throws MyException {
        new PrintStmt(new ValueExp(new IntValue(1))).execute(state);
        new PrintStmt(new ValueExp(new IntValue(2))).execute(state);

        assertEquals(new IntValue(1), state.getOut().get(0));
        assertEquals(new IntValue(2), state.getOut().get(1));
    }

    @Test
    void printOfUndefinedVariableThrowsAndLeavesOutEmpty() {
        var ex = assertThrows(ExpressionException.class,
                () -> new PrintStmt(new VarExp("x")).execute(state));
        assertEquals("Variable x is not defined!", ex.getMessage());
        assertEquals(0, state.getOut().size());
    }

}