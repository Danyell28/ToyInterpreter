package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;

import java.util.Optional;

public record IfStmt(IExp cond, IStmt thenS, IStmt elseS) implements IStmt{

    @Override
    public Optional<PrgState> execute(PrgState state) throws MyException {
        var c = cond.eval(state.getSymTable(),state.getHeap() );
        if ( !c.getType().equals(new BoolValue(true).getType()) )
            throw new StatementException("Condition is not a boolean");
        if ( c.equals(new BoolValue(true)))
            state.getExeStack().push(thenS);
        else
            state.getExeStack().push(elseS);
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "( if " + cond + " then " + thenS + " else " + elseS + ") ";
    }
}
