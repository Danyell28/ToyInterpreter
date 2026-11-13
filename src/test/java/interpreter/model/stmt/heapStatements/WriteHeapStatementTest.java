package interpreter.model.stmt.heapStatements;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WriteHeapStatementTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = PrgState.of(new NopStmt());
    }

    @Test
    void variableNotDefinedException(){
        var ex = assertThrows(StatementException.class, () -> new WriteHeapStatement("x", new ValueExp(new IntValue(5))).execute(state));
        assertEquals("writeHeap: x is not declared", ex.getMessage());
    }

    @Test
    void variableNotReferenceException() throws MyException {
        new VarDeclStmt("x", new IntType()).execute(state);
        var ex = assertThrows(StatementException.class, () -> new WriteHeapStatement("x", new ValueExp(new IntValue(5))).execute(state));
        assertEquals("writeHeap: x is not a reference", ex.getMessage());
    }

    @Test
    void addressNotInHeapException() throws MyException {
        new VarDeclStmt("x", new RefType(new IntType())).execute(state);
        var ex = assertThrows(StatementException.class, () -> new WriteHeapStatement("x", new ValueExp(new IntValue(5))).execute(state));
        assertEquals("writeHeap: x address is not in the heap", ex.getMessage());
    }

    @Test
    void expressionTypeMismatchException() throws MyException {
        new VarDeclStmt("x", new RefType( new RefType(new IntType()))).execute(state);
        new NewStmt("x", new ValueExp(new RefValue(0, new IntType()))).execute(state);
        IExp exp = new ValueExp(new IntValue(5));
        var ex = assertThrows(StatementException.class, () -> new WriteHeapStatement("x", exp).execute(state));
        assertEquals("writeHeap: " + exp + " has type int, expected Ref(int)", ex.getMessage());
    }

    @Test
    void writesCorrectly() throws MyException {
        new VarDeclStmt("x", new RefType(new RefType(new IntType()))).execute(state);
        new NewStmt("x", new ValueExp(new RefValue(0, new IntType()))).execute(state);

        RefValue before = (RefValue) state.getSymTable().lookup("x");

        RefValue newValue = new RefValue(3, new IntType());   // type Ref(int), matches the cell
        new WriteHeapStatement("x", new ValueExp(newValue)).execute(state);

        RefValue after = (RefValue) state.getSymTable().lookup("x");
        assertEquals(before, after);                                   // x still points to the same cell
        assertEquals(newValue, state.getHeap().get(after.address()));  // the cell now holds the new value
    }
}