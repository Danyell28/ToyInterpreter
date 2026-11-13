package interpreter.controller;

import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

import java.util.*;
import java.util.stream.Collectors;

public final class GarbageCollector {
    private GarbageCollector() {}
    /** The addresses that occur in the RefValues of the SymTable. */
    public static Set<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).address())
                .collect(Collectors.toSet());
    }
    /** UNSAFE: keeps only the heap entries referred to directly from the SymTable. */
    public static Map<Integer, IValue> unsafeGarbageCollector(Set<Integer> symTableAddr,
                                                              Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Set<Integer> reachableAddresses(Set<Integer> roots, Map<Integer, IValue> heap) {
        Set<Integer> reachable = new HashSet<>(roots);
        Deque<Integer> toVisit = new ArrayDeque<>(roots);
        while (!toVisit.isEmpty()) {
            int address = toVisit.pop();
            if (heap.get(address) instanceof RefValue ref && reachable.add(ref.address()))
                toVisit.push(ref.address());
        }
        return reachable;
    }


    public static Map<Integer, IValue> safeGarbageCollector(Set<Integer> roots,
                                                            Map<Integer, IValue> heap) {
        Set<Integer> reachable = reachableAddresses(roots, heap);
        return heap.entrySet().stream()
                .filter(e -> reachable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
