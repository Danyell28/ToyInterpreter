package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

public record CompStmt(IStmt first, IStmt second) implements IStmt{

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }
}
