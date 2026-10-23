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

class ArithExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void firstOperandNotOfIntType(){
        IExp e = new ArithExp(ArithOp.ADD, new ValueExp(new BoolValue(true)), new ValueExp(new IntValue(1)));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable));
        assertEquals("first operand is not an integer", ex.getMessage());
    }

    @Test
    void secondOperandNotOfIntType(){
        IExp e = new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(1)), new ValueExp(new BoolValue(true)));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable));
        assertEquals("second operand is not an integer", ex.getMessage());
    }

    @Test
    void additionCorrectResult() throws ExpressionException {
        IExp e = new ArithExp(ArithOp.ADD, new ValueExp( new IntValue(6)), new ValueExp(new IntValue(7)));
        IValue result = e.eval(symTable);
        assertEquals( new IntValue(13), result);
    }

    @Test
    void substractionCorrectResult() throws ExpressionException {
        IExp e = new ArithExp(ArithOp.SUB, new ValueExp( new IntValue(6)), new ValueExp(new IntValue(7)));
        IValue result = e.eval(symTable);
        assertEquals( new IntValue(-1), result);
    }

    @Test
    void multiplicationCorrectResult() throws ExpressionException {
        IExp e = new ArithExp(ArithOp.MUL, new ValueExp( new IntValue(6)), new ValueExp(new IntValue(7)));
        IValue result = e.eval(symTable);
        assertEquals( new IntValue(42), result);
    }

    @Test
    void divisionCorrectResult() throws ExpressionException {
        IExp e = new ArithExp(ArithOp.DIV, new ValueExp( new IntValue(42)), new ValueExp(new IntValue(6)));
        IValue result = e.eval(symTable);
        assertEquals( new IntValue(7), result);
    }

    @Test
    void divisionByZeroExpression() {
        IExp e = new ArithExp(ArithOp.DIV, new ValueExp(new IntValue(1)), new ValueExp(new IntValue(0)) );
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable));
        assertEquals("division by zero", ex.getMessage());
    }


}