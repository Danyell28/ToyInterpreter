package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

import java.util.List;

public interface IRepository {
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgList);
    void logPrgStateExec(PrgState state) throws MyException;

}
