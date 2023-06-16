package uy.edu.um.prog2.adt;

import uy.edu.um.prog2.adt.exceptions.EmptyTreeException;

public interface BinarySearchTree<K extends Comparable<K>, T> {

    boolean find(K key);

    void insert(K key, T value);

    void delete(K key) throws EmptyTreeException;

    T findMax();

    T findMin();

}
