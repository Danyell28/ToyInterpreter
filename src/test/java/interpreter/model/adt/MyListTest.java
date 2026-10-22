package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyListTest {
    private IList<Integer> list ;

    @BeforeEach
    void setUp(){
        list = new MyList<>();
    }

    @Test
    void add() throws ADTException {
        list.add(3);
        list.add(10);
        assertEquals(3,list.get(0));
        assertEquals(10,list.get(1));
    }

    @Test
    void getInvalidIndexException() {
        list.add(3);
        list.add(10);
        //the size should be 2 by now, so any invalid index is either <=0 or >=2
        var ex = assertThrows(ADTException.class, () -> list.get(2));
        assertEquals("Index is not valid!", ex.getMessage());

        var ex1 = assertThrows(ADTException.class, () -> list.get(-1));
        assertEquals("Index is not valid!", ex1.getMessage());
    }

    @Test
    void get() throws ADTException {
        list.add(3);
        list.add(10);
        list.add(-7);

        assertEquals(-7, list.get(2));
    }

    @Test
    void size() {
        list.add(3);
        list.add(10);
        list.add(-7);

        assertEquals(3, list.size());
    }
}