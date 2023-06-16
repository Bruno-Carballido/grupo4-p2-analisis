package uy.edu.um.prog2.adt;

import uy.edu.um.prog2.adt.exceptions.DatosIncorrectos;
import uy.edu.um.prog2.adt.exceptions.EmptyQueueException;

public interface Queue<T> {
    void enqueue(T value);

    T dequeue() throws EmptyQueueException;

    boolean contains(T value);

    int size();

    T get(int position) throws DatosIncorrectos;
}
