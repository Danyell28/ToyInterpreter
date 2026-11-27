package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

import java.util.Optional;

public record NopStmt() implements IStmt{

    @Override
    public Optional<PrgState> execute(PrgState state) throws MyException {
        return Optional.empty();
    }

    @Override
    public String toString(){
        return "";
    }
}
