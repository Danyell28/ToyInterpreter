package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarExpTest {
    private IDictionary<String, IValue> symTable = new MyDictionary<>();
    private IHeap heap = new MyHeap();

    @Test
    void checksUndefinedVariable() {
        IExp v = new VarExp("v");
        var ex = assertThrows(ExpressionException.class, () -> v.eval(symTable,heap ));
        assertEquals("Variable v is not defined!", ex.getMessage());
    }
}