package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {
    private IStack<Integer> stack;

    @BeforeEach
    void setUp(){
        stack = new MyStack<>();
    }

    @Test
    void pushCheckElementsWerePushedInTheRightOrder() throws ADTException {
        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    @Test
    void popCheckPopEmptyStackException(){
        var ex = assertThrows(ADTException.class, () -> stack.pop());
        assertEquals("pop from an empty stack", ex.getMessage());
    }

    @Test
    void popCheckCorrectElementWasPopped() throws ADTException {
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.pop());

    }

    @Test
    void isEmptyStackReturnsTheCheckCorrectly() throws ADTException {
        assertTrue(stack.isEmpty());

        stack.push(6);
        stack.push(7);
        stack.pop();
        assertFalse(stack.isEmpty());

        stack.pop();
        assertTrue(stack.isEmpty());
    }
}