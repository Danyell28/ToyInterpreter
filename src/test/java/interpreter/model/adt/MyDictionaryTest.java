package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {
    private IDictionary<Integer, Integer> dict;

    @BeforeEach
    void setUp() {
        dict = new MyDictionary<>();
    }

    @Test
    void isDefined() {
        dict.put(5,10);
        dict.put(7,4);

        assertTrue(dict.isDefined(5));
        assertFalse(dict.isDefined(4));
    }

    @Test
    void lookupOfMissingKeyThrows(){
        var ex = assertThrows(ADTException.class, () -> dict.lookup(4));
        assertEquals("Key does not exist!", ex.getMessage());
    }

    @Test
    void lookupGetValues() throws ADTException {
        dict.put(5,10);
        dict.put(7,4);
        assertEquals(10, dict.lookup(5));
        assertEquals(4, dict.lookup(7));
    }

    @Test
    void put() throws ADTException {
        dict.put(5,10);
        assertEquals(10, dict.lookup(5));
        dict.put(5,17);
        assertEquals(17, dict.lookup(5));
    }

    @Test
    void updateOnMissingKeyThrows() {
        var ex = assertThrows(ADTException.class, () -> dict.update(5,6));
        assertEquals("Key does not exist!", ex.getMessage());
    }

    @Test
    void updateOnValidKey() throws ADTException{
        dict.put(5, 10);
        dict.update(5, 17);
        assertEquals(17, dict.lookup(5));
    }
}