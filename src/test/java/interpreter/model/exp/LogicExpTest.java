package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void firstOperandNotOfBooleanType(){
        IExp e = new LogicExp(LogicOp.AND, new ValueExp(new IntValue(4)), new ValueExp( new BoolValue(true)));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable));
        assertEquals("First operand is not boolean", ex.getMessage());
    }

    @Test
    void secondOperandNotOfBooleanType(){
        IExp e = new LogicExp(LogicOp.AND, new ValueExp( new BoolValue(true)), new ValueExp(new IntValue(4)));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable));
        assertEquals("Second operand is not boolean", ex.getMessage());
    }

    @Test
    void conjunctionOperationCorrectResult() throws MyException {
        IExp e1 = new LogicExp(LogicOp.AND, new ValueExp( new BoolValue(false)), new ValueExp(new BoolValue(false)));
        assertEquals(new BoolValue(false), e1.eval(symTable));

        IExp e2 = new LogicExp(LogicOp.AND, new ValueExp( new BoolValue(false)), new ValueExp(new BoolValue(true)));
        assertEquals(new BoolValue(false), e2.eval(symTable));

        IExp e3 = new LogicExp(LogicOp.AND, new ValueExp( new BoolValue(true)), new ValueExp(new BoolValue(false)));
        assertEquals(new BoolValue(false), e3.eval(symTable));

        IExp e4 = new LogicExp(LogicOp.AND, new ValueExp( new BoolValue(true)), new ValueExp(new BoolValue(true)));
        assertEquals(new BoolValue(true), e4.eval(symTable));
    }

    @Test
    void disjunctionOperationCorrectResult() throws MyException {
        IExp e1 = new LogicExp(LogicOp.OR, new ValueExp( new BoolValue(false)), new ValueExp(new BoolValue(false)));
        assertEquals(new BoolValue(false), e1.eval(symTable));

        IExp e2 = new LogicExp(LogicOp.OR, new ValueExp( new BoolValue(false)), new ValueExp(new BoolValue(true)));
        assertEquals(new BoolValue(true), e2.eval(symTable));

        IExp e3 = new LogicExp(LogicOp.OR, new ValueExp( new BoolValue(true)), new ValueExp(new BoolValue(false)));
        assertEquals(new BoolValue(true), e3.eval(symTable));

        IExp e4 = new LogicExp(LogicOp.OR, new ValueExp( new BoolValue(true)), new ValueExp(new BoolValue(true)));
        assertEquals(new BoolValue(true), e4.eval(symTable));
    }

}