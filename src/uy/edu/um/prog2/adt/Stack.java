package uy.edu.um.prog2.adt;

import uy.edu.um.prog2.adt.exceptions.DatosIncorrectos;
import uy.edu.um.prog2.adt.exceptions.EmptyStackException;

public interface Stack<T> {
    void push(T value);

    T pop() throws EmptyStackException;

    T peek() throws EmptyStackException;

    int size();

    T get(int position) throws DatosIncorrectos;
    boolean contains(T value);
}
