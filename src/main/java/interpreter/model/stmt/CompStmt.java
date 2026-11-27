package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

import java.util.Optional;

public record CompStmt(IStmt first, IStmt second) implements IStmt{

    @Override
    public Optional<PrgState> execute(PrgState state) throws MyException {
        state.getExeStack().push(second);
        state.getExeStack().push(first);
        return Optional.empty();
    }

    @Override
    public String toString(){
        return "(" + first + ";" + second + ") ";
    }
}
