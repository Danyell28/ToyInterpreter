package interpreter.model;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.StatementException;
import interpreter.model.stmt.NopStmt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrgStateTest {
    private PrgState state;
    @BeforeEach
    void setUp() throws ADTException {
        state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    void testDistinctIds(){
        PrgState newState = PrgState.of(new NopStmt());
        assertTrue(state.getId() != newState.getId());

        PrgState newState2 = PrgState.of(new NopStmt());
        assertTrue(newState.getId() != newState2.getId());
        assertTrue(state.getId() != newState2.getId());
    }

    @Test
    void oneStepOnEmptyStackException(){
        var ex = assertThrows(StatementException.class, () -> state.oneStep());
        assertEquals("Program state stack is empty!", ex.getMessage());
    }

}