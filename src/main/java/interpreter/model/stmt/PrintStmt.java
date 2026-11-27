package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;

import java.util.Optional;

public record PrintStmt(IExp exp) implements IStmt {

    @Override
    public Optional<PrgState> execute(PrgState state) throws MyException {
        state.getOut().add(exp.eval(state.getSymTable(), state.getHeap()));
        return Optional.empty();
    }

    @Override
    public String toString(){
        return " print(" + exp + ") ";
    }

}
