package interpreter.model.type;

import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringTypeTest {
    @Test
    void checkDefaultValueIsEmptyString(){
        IType var = new StringType();
        assertEquals(new StringValue(""), var.defaultValue());
    }
}