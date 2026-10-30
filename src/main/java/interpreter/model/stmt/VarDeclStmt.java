package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.type.IType;

public record VarDeclStmt(String id, IType type) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        if(symTable.isDefined(id))
            throw new StatementException("Variable "+id+" already declared");
        symTable.put(id, type.defaultValue());
        return state;
    }

    @Override
    public String toString(){
        return type + " " + id + " ";
    }
}
