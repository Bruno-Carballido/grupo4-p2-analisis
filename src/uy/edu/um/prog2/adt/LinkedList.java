package uy.edu.um.prog2.adt;

import uy.edu.um.prog2.adt.exceptions.DatosIncorrectos;

public interface LinkedList<T> {

    void add(T value);

    T get(int position) throws DatosIncorrectos;

    boolean contains(T value);

    void remove(T value);

    int size();
}
