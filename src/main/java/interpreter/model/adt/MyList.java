package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T>{
    private final ArrayList<T> l = new ArrayList<>();

    @Override
    public void add(T el) {
        l.add(el);
    }

    @Override
    public T get(int index) throws ADTException {
        if ( index < 0 || l.size() <= index)
            throw new ADTException("Index is not valid!");
        return l.get(index);
    }

    @Override
    public int size() {
        return l.size();
    }

    @Override
    public String toString(){
        return l.toString();
    }

    @Override
    public List<T> toList(){
        return List.copyOf(l);
    }
}
