package interpreter.model.stmt.heapStatements;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

import java.util.Optional;

public record WriteHeapStatement(String varName, IExp exp) implements IStmt {
    @Override
    public Optional<PrgState> execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        if ( ! symTable.isDefined(varName))
            throw new StatementException("writeHeap: " + varName + " is not declared");
        if ( ! ( symTable.lookup(varName) instanceof RefValue ref))
            throw new StatementException("writeHeap: " + varName + " is not a reference");
        IValue value = exp.eval(symTable, state.getHeap());
        if( !(state.getHeap().isDefined(ref.address())))
            throw new StatementException("writeHeap: " + varName + " address is not in the heap");
        if(! value.getType().equals(ref.locationType()))
            throw new StatementException("writeHeap: " + exp + " has type " + value.getType()
                    + ", expected " + ref.locationType());
        state.getHeap().update(ref.address(), value);
        return Optional.empty();
    }

    @Override
    public String toString(){
        return "wh(" + varName + "," + exp + ")";
    }
}
