package uy.edu.um.prog2.adt;

import uy.edu.um.prog2.adt.exceptions.EmptyHeapException;

public interface Heap<K extends Comparable<K>, T> {

    void insert(K key, T value);

    void delete() throws EmptyHeapException;

    T getMaxValue();

    int size();

}
