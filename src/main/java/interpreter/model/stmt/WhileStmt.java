package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.BoolType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

import java.util.Optional;

public record WhileStmt(IExp cond, IStmt body) implements IStmt{
    @Override
    public Optional<PrgState> execute(PrgState state) throws MyException {
        IValue value = cond.eval(state.getSymTable(), state.getHeap());
        if(! value.getType().equals(new BoolType()))
            throw new StatementException("while: " + cond + " is not a boolean");
        if ( value.equals(new BoolValue(true))){
            state.getExeStack().push(this);
            state.getExeStack().push(body);
        }
        return Optional.empty();
    }

    @Override
    public String toString(){
        return "(while (" + cond +") " + body + ")";
    }
}
