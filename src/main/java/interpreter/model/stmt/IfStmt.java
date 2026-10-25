package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.BoolType;
import interpreter.model.value.BoolValue;

public record IfStmt(IExp cond, IStmt thenS, IStmt elseS) implements IStmt{

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var c = cond.eval(state.getSymTable());
        if ( !c.getType().equals(new BoolValue(true).getType()) )
            throw new StatementException("Condition is not a boolean");
        if ( c.equals(new BoolValue(true)))
            state.getExeStack().push(thenS);
        else
            state.getExeStack().push(elseS);
        return state;
    }

    @Override
    public String toString() {
        return "( if " + cond + " then " + thenS + " else " + elseS + ") ";
    }
}
