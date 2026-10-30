package interpreter.model.stmt.fileStatements;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.stmt.IStmt;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        if( !state.getSymTable().isDefined(varName))
            throw new StatementException("Variable " + varName + " is not declared");
        if( !state.getSymTable().lookup(varName).getType().equals(new IntType()))
            throw new StatementException("Variable " + varName + " is not an integer");

        if(! (exp.eval(state.getSymTable()) instanceof StringValue fileName))
            throw new StatementException("readFile: " + exp + " is not a string");
        if( ! state.getFileTable().isDefined(fileName))
            throw new StatementException("readFile: " + fileName + " is not opened");
        try{
            String line = state.getFileTable().lookup(fileName).readLine();
            if( line == null)
                state.getSymTable().update(varName, new IntValue(0));
            else
                state.getSymTable().update(varName, new IntValue(Integer.parseInt(line.strip())));

        }
        catch(IOException | NumberFormatException e){
            throw new StatementException("readFile: could not read from " + fileName + ": " + e.getMessage());
        }
        return state;
    }

    @Override
    public String toString(){
        return "readFile(" + exp + "," + varName + ")";
    }

}
