package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.HashMap;

public class MyDictionary<K, V> implements IDictionary<K, V>{
    private final HashMap<K, V> dict = new HashMap<>();

    @Override
    public boolean isDefined(K key) {
        return dict.containsKey(key);
    }

    @Override
    public V lookup(K key) throws ADTException {
        if ( !dict.containsKey(key) )
            throw new ADTException("Key does not exist!");
        return dict.get(key);
    }

    @Override
    public void put(K key, V value) {
        dict.put(key, value);
    }

    @Override
    public void update(K key, V value) throws ADTException{
        if ( !dict.containsKey(key) )
            throw new ADTException("Key does not exist!");
        dict.put(key, value);
    }

    @Override
    public String toString(){
        return dict.toString();
    }
}
