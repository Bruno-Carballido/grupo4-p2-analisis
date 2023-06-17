package uy.edu.um.prog2.adt;

import uy.edu.um.prog2.adt.exceptions.EmptyHashException;

public interface Hash<K, T> {

    void put(K key, T value) throws EmptyHashException;

    void update(K key, T value) throws EmptyHashException;

    T get(K key) throws EmptyHashException;

    void remove(K key);

    int size();

}
