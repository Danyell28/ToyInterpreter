package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void firstOperandNotIntegerException(){
        IExp e = new RelationalExp(RelOp.EQ, new ValueExp(new StringValue("x")), new ValueExp(new IntValue(5)));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable));
        assertEquals("first operand is not an integer", ex.getMessage());
    }

    @Test
    void secondOperandNotIntegerException(){
        IExp e = new RelationalExp(RelOp.EQ, new ValueExp(new IntValue(5)), new ValueExp(new StringValue("x")));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable));
        assertEquals("second operand is not an integer", ex.getMessage());
    }

    @Test
    void lessThanCorrectResult() throws MyException {
        IExp e1 = new RelationalExp(RelOp.LT, new ValueExp(new IntValue(6)), new ValueExp(new IntValue(7)));
        assertEquals(new BoolValue(true), e1.eval(symTable));

        IExp e2 = new RelationalExp(RelOp.LT, new ValueExp(new IntValue(7)), new ValueExp(new IntValue(7)));
        assertEquals(new BoolValue(false), e2.eval(symTable));

        IExp e3 = new RelationalExp(RelOp.LT, new ValueExp(new IntValue(7)), new ValueExp(new IntValue(6)));
        assertEquals(new BoolValue(false), e3.eval(symTable));
    }

    @Test
    void lessEqualCorrectResult() throws MyException {
        IExp e1 = new RelationalExp(RelOp.LE, new ValueExp(new IntValue(6)), new ValueExp(new IntValue(7)));
        assertEquals(new BoolValue(true), e1.eval(symTable));

        IExp e2 = new RelationalExp(RelOp.LE, new ValueExp(new IntValue(7)), new ValueExp(new IntValue(7)));
        assertEquals(new BoolValue(true), e2.eval(symTable));

        IExp e3 = new RelationalExp(RelOp.LE, new ValueExp(new IntValue(7)), new ValueExp(new IntValue(6)));
        assertEquals(new BoolValue(false), e3.eval(symTable));
    }

    @Test
    void equalCorrectResult() throws MyException {
        IExp e1 = new RelationalExp(RelOp.EQ, new ValueExp(new IntValue(6)), new ValueExp(new IntValue(7)));
        assertEquals(new BoolValue(false), e1.eval(symTable));

        IExp e2 = new RelationalExp(RelOp.EQ, new ValueExp(new IntValue(7)), new ValueExp(new IntValue(7)));
        assertEquals(new BoolValue(true), e2.eval(symTable));
    }

    @Test
    void notEqualCorrectResult() throws MyException {
        IExp e1 = new RelationalExp(RelOp.NE, new ValueExp(new IntValue(6)), new ValueExp(new IntValue(7)));
        assertEquals(new BoolValue(true), e1.eval(symTable));

        IExp e2 = new RelationalExp(RelOp.NE, new ValueExp(new IntValue(7)), new ValueExp(new IntValue(7)));
        assertEquals(new BoolValue(false), e2.eval(symTable));
    }

    @Test
    void greaterThanCorrectResult() throws MyException {
        IExp e = new RelationalExp(RelOp.GT, new ValueExp(new IntValue(7)), new ValueExp(new IntValue(6)));
        assertEquals(new BoolValue(true), e.eval(symTable));

        IExp e3 = new RelationalExp(RelOp.GT, new ValueExp(new IntValue(6)), new ValueExp(new IntValue(7)));
        assertEquals(new BoolValue(false), e3.eval(symTable));
    }

    @Test
    void greaterEqualCorrectResult() throws MyException {
        IExp e1 = new RelationalExp(RelOp.GE, new ValueExp(new IntValue(7)), new ValueExp(new IntValue(6)));
        assertEquals(new BoolValue(true), e1.eval(symTable));

        IExp e2 = new RelationalExp(RelOp.GE, new ValueExp(new IntValue(7)), new ValueExp(new IntValue(7)));
        assertEquals(new BoolValue(true), e2.eval(symTable));

        IExp e3 = new RelationalExp(RelOp.GE, new ValueExp(new IntValue(6)), new ValueExp(new IntValue(7)));
        assertEquals(new BoolValue(false), e3.eval(symTable));
    }

}