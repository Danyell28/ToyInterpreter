package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements IDictionary<K, V>{
    private HashMap<K, V> dict = new HashMap<>();

    @Override
    public synchronized boolean isDefined(K key) {
        return dict.containsKey(key);
    }

    @Override
    public synchronized V lookup(K key) throws ADTException {
        if ( !dict.containsKey(key) )
            throw new ADTException("Key does not exist!");
        return dict.get(key);
    }

    @Override
    public synchronized void put(K key, V value) {
        dict.put(key, value);
    }

    @Override
    public synchronized void update(K key, V value) throws ADTException{
        if ( !dict.containsKey(key) )
            throw new ADTException("Key does not exist!");
        dict.put(key, value);
    }

    @Override
    public synchronized String toString(){
        return dict.toString();
    }

    @Override
    public synchronized void remove(K key) throws ADTException {
        if ( !dict.containsKey(key) )
            throw new ADTException("Key does not exist!");
        dict.remove(key);
    }

    @Override
    public synchronized Map<K, V> toMap(){
        return Map.copyOf(dict);
    }

    public synchronized void setDict(HashMap<K, V> other){
        this.dict = new HashMap<>(other);
    }

    @Override
    public synchronized IDictionary<K, V> copy(){
        IDictionary<K, V> copyDict = new MyDictionary<>();
        copyDict.setDict(this.dict);
        return copyDict;
    }
}
