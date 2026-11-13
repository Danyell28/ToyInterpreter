package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.stmt.heapStatements.NewStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReadHeapExpTest {
    private IDictionary<String, IValue> symTable;
    private IHeap heap;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
        heap = new MyHeap();
    }

    @Test
    void expressionIsNotAReferenceException(){
        IExp exp = new ValueExp(new IntValue(5));
        var ex = assertThrows(ExpressionException.class, () -> new ReadHeapExp(exp).eval(symTable, heap));
        assertEquals("readHeap: " + exp + " is not a reference", ex.getMessage());
    }

    @Test
    void addressNotInHeapException(){
        IExp exp = new ValueExp(new RefValue(2, new IntType()));
        var ex = assertThrows(ExpressionException.class, () -> new ReadHeapExp(exp).eval(symTable, heap));
        assertEquals("readHeap: " + exp + " address is not in the heap", ex.getMessage());
    }

    @Test
    void correctlyEvaluatesExpression() throws MyException {
        PrgState state = PrgState.of(new NopStmt());
        new VarDeclStmt("x", new RefType(new IntType())).execute(state);
        new NewStmt("x", new ValueExp(new IntValue((7)))).execute(state);

        IValue result = new ReadHeapExp(new VarExp("x")).eval(state.getSymTable(), state.getHeap());
        assertEquals(new IntValue(7), result);
    }
}