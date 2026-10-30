package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

public interface IRepository {
    public PrgState getCrtPrg();
    public void logPrgStateExec() throws MyException;
}
