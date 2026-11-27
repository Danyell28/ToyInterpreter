package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.HashMap;
import java.util.Map;

public interface IDictionary<K, V> {
    boolean isDefined(K key);
    V lookup(K key) throws ADTException;
    void put(K key, V value);
    void update(K key, V value) throws ADTException;
    void remove(K key) throws ADTException; // new: missing key -> ADTException
    Map<K, V> toMap();
    IDictionary<K, V> copy();
    void setDict(HashMap<K, V> other);
}
