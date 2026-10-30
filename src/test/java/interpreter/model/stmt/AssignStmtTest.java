package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws ADTException {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new AssignStmt("dummy", new VarExp("dummy")));
        state.getExeStack().pop();
    }

    @Test
    void variableNotDefined(){
        IStmt statement = new AssignStmt("x", new ValueExp(new IntValue(15)));
        var ex = assertThrows(StatementException.class, () -> statement.execute(state));
        assertEquals("Variable x is not declared", ex.getMessage());
    }

    @Test
    void assignmentOfWrongTypeThrows(){
        state.getSymTable().put("x", new BoolValue(true));
        IStmt statement = new AssignStmt("x", new ValueExp(new IntValue(15)));
        var ex = assertThrows(StatementException.class, () -> statement.execute(state));
        assertEquals("declared type of variable x and type of the assigned expression do not match",
                ex.getMessage());
    }

    @Test
    void checkCorrectResultOfRule() throws MyException{
        state.getSymTable().put("x", new BoolValue(true));
        IStmt statement1 = new AssignStmt("x", new ValueExp(new BoolValue(false)));
        statement1.execute(state);

        assertEquals(new BoolValue(false), state.getSymTable().lookup("x"));

        state.getSymTable().put("y", new IntValue(0));
        IStmt statement2 = new AssignStmt("y", new ValueExp(new IntValue(15)));
        statement2.execute(state);

        assertEquals(new IntValue(15), state.getSymTable().lookup("y"));
    }
}