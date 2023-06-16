package uy.edu.um.prog2.adt;

import uy.edu.um.prog2.adt.exceptions.DatosIncorrectos;
import uy.edu.um.prog2.adt.exceptions.EmptyStackException;

public class StackImpl<T> implements Stack<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public StackImpl() {
        this.first = null;
        this.size = 0;
        this.last = null;
    }

    @Override
    public void push(T value) {

        if (value != null) {

            Node<T> newNode = new Node<>(value);

            if (this.first == null) { // si la pila es vacia

                this.first = newNode;
                this.last = newNode;


            } else { // en caso de no ser vacia se agrega al final
                Node<T> curr = this.first;
                while (curr.getNext() != null) {
                    curr = curr.getNext();
                }
                curr.setNext(newNode);
                this.last = newNode;
            }
            this.size++;
        }
        // si el elemento es vacio se ignora
    }


    @Override
    public T pop() throws EmptyStackException {

        if (this.last == null) {
            throw new EmptyStackException();
        }

        T valueToRemove = this.last.getValue();

        if (this.first == this.last) {
            // Si la pila solo contiene un elemento, vaciamos la pila
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
    public T peek() {
        T valueToReturn = null;

        if (this.last != null) {
            valueToReturn = this.last.getValue();
        }

        return valueToReturn;

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
