package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyStack;

import java.util.Optional;

public record ForkStmt(IStmt stmt) implements IStmt {

    @Override
    public Optional<PrgState> execute(PrgState state) throws MyException {
        PrgState child = new PrgState(new MyStack<>(),
                    state.getSymTable().copy(),
                    state.getOut(),
                    state.getFileTable(),
                    state.getHeap(),
                    stmt);
        return Optional.of(child);
    }

    @Override
    public String toString(){
        return "fork(" + stmt + ")";
    }
}
