package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;

import javax.swing.text.html.Option;
import java.util.Optional;

public record AssignStmt(String id, IExp exp) implements IStmt {

    @Override
    public Optional<PrgState> execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        if( !symTable.isDefined(id))
            throw new StatementException("Variable "+id+" is not declared");
        IValue val = exp.eval(symTable, state.getHeap() );
        if( !val.getType().equals(symTable.lookup(id).getType()))
            throw new StatementException("declared type of variable " + id
                    + " and type of the assigned expression do not match");
        symTable.update(id, val);
        return Optional.empty();
    }

    @Override
    public String toString(){
        return id + " = " + exp + " ";
    }
}
