package interpreter.model.stmt.heapStatements;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewStmtTest {
    private PrgState state;
    @BeforeEach
    void setUp() {
        state = PrgState.of(new NopStmt());
    }

    @Test
    void variableNotDeclaredException(){
        var ex = assertThrows(StatementException.class, () ->  new NewStmt("x", new ValueExp(new IntValue(0))).execute(state));
        assertEquals("new: x is not declared", ex.getMessage());
    }

    @Test
    void variableNotReferenceException() throws MyException {
        new VarDeclStmt("x", new IntType()).execute(state);
        var ex = assertThrows(StatementException.class, () ->  new NewStmt("x", new ValueExp(new IntValue(0))).execute(state));
        assertEquals("new: x is not a reference", ex.getMessage());
    }

    @Test
    void notCorrectReferenceTypeException() throws MyException {
        new VarDeclStmt("x", new RefType(new IntType())).execute(state);
        var ex = assertThrows(StatementException.class, () ->  new NewStmt("x", new ValueExp(new StringValue("test"))).execute(state));
        assertEquals("new: \"test\" has type string, expected int", ex.getMessage());
    }

    @Test
    void actuallyAllocatesAndUpdatesTheVariable() throws MyException {
        new VarDeclStmt("x", new RefType(new IntType())).execute(state);
        new NewStmt("x", new ValueExp(new IntValue(5))).execute(state);
        IValue ref = state.getSymTable().lookup("x");
        assertEquals( new RefValue(1, new IntType()), ref);
        assertEquals( new IntValue(5), state.getHeap().get(1));
    }
}