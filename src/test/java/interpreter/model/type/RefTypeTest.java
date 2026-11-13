package interpreter.model.type;

import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefTypeTest {

    @Test
    void defaultValue() {
        IType inner = new IntType();
        IType t = new RefType(inner);
        assertEquals(new RefValue(0, inner), t.defaultValue());
    }

    @Test
    void testNestedRefTypes(){
        IType inner = new IntType();
        IType ref = new RefType( new RefType(inner));
        assertEquals(new RefType( new RefType( new IntType())), ref);
        assertNotEquals(new RefType(new IntType()), ref);

    }
}