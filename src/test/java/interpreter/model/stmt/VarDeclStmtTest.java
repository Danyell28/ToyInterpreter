package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.type.StringType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarDeclStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws ADTException {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),  new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    void variableAlreadyDeclaredException(){
        state.getSymTable().put("x", new IntValue(15));
        IStmt statement = new VarDeclStmt("x", new IntType());

        var ex = assertThrows(StatementException.class, () -> statement.execute(state));
        assertEquals("Variable x already declared", ex.getMessage());
    }

    @Test
    void variableDeclaredCorrectly() throws MyException {
        IStmt statement1 = new VarDeclStmt("x", new IntType());
        statement1.execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));

        IStmt statement2 = new VarDeclStmt("y", new BoolType());
        statement2.execute(state);
        assertEquals(new BoolValue(false), state.getSymTable().lookup("y"));

        IStmt statement3 = new VarDeclStmt("z", new StringType());
        statement3.execute(state);
        assertEquals(new StringValue(""), state.getSymTable().lookup("z"));
    }
}