package uy.edu.um.prog2.adt;

import uy.edu.um.prog2.adt.exceptions.DatosIncorrectos;
import uy.edu.um.prog2.adt.exceptions.EmptyQueueException;

public class QueueImpl<T> implements Queue<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public QueueImpl() {
        this.first = null;
        this.size = 0;
        this.last = null;
    }

    @Override
    public void enqueue(T value) {
        Node<T> newNode = new Node<>(value);

        if (this.first == null) {
            // Si la cola está vacía, el nuevo nodo se convierte en el primer y último nodo
            this.first = newNode;
            this.last = newNode;
        } else {
            // El nuevo nodo se coloca al inicio de la cola
            newNode.setNext(this.first);
            this.first = newNode;
        }

        this.size++;
    }


    @Override
    public T dequeue() throws EmptyQueueException {
        if (this.last == null) {
            throw new EmptyQueueException();
        }

        T valueToRemove = this.last.getValue();

        if (this.first == this.last) {
            // Si la cola solo contiene un elemento, vaciamos la pila
            this.first = null;
            this.last = null;
        } else {
            // Buscamos el nodo anterior al último nodo
            Node<T> prevNode = this.first;
            while (prevNode.getNext() != this.last) {
                prevNode = prevNode.getNext();
            }

            // Eliminamos el último nodo
            prevNode.setNext(null);
            this.last = prevNode;
        }

        this.size--;
        return valueToRemove;
    }


    @Override
    public boolean contains(T value) {
        boolean contains = false;
        Node<T> aux = this.first;

        while (aux != null) {
            if (aux.getValue().equals(value)) {
                contains = true;
                break;
            }
            aux = aux.getNext();
        }
        return contains;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T get(int position) throws DatosIncorrectos {
        if (position < 0 || position >= this.size) {
            throw new DatosIncorrectos();
        }
        Node<T> aux = this.first;
        // Se busca el nodo que corresponde con la posición
        for (int i = 0; i < position; i++) {
            aux = aux.getNext();
        }
        // Se retorna el valor del nodo en la posición especificada
        return aux.getValue();
    }
}
