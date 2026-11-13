package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MyHeapTest {
    private IHeap heap;

    @BeforeEach
    void setUp(){
        heap = new MyHeap();
    }

    @Test
    void allocateGivesCorrectAddresses(){
        assertEquals(1,heap.allocate(new IntValue(0)));
        assertEquals(2,heap.allocate(new IntValue(0)));
        assertEquals(3,heap.allocate(new IntValue(0)));
    }

    @Test
    void getOnInvalidAddress(){
        var ex = assertThrows(ADTException.class, () -> heap.get(4));
        assertEquals("Key does not exist", ex.getMessage());
    }

    @Test
    void getCorrectResult() throws ADTException {
        heap.allocate(new IntValue(1));
        heap.allocate(new IntValue(4));
        heap.allocate(new IntValue(7));

        assertEquals(new IntValue(4), heap.get(2));
    }

    @Test
    void updateOnInvalidAddress(){
        var ex = assertThrows(ADTException.class, () -> heap.update(4, new IntValue(0)));
        assertEquals("Key does not exist", ex.getMessage());
    }

    @Test
    void updateCorrectResult() throws ADTException {
        heap.allocate(new IntValue(1));
        heap.allocate(new IntValue(4));
        heap.allocate(new IntValue(7));
        assertEquals(new IntValue(4), heap.get(2));

        heap.update(2, new IntValue(10));
        assertEquals(new IntValue(10), heap.get(2));
    }

    @Test
    void isDefinedCorrectExecution(){
        heap.allocate(new IntValue(1));
        heap.allocate(new IntValue(4));
        heap.allocate(new IntValue(7));

        assertTrue(heap.isDefined(3));
        assertFalse(heap.isDefined(4));
    }
}