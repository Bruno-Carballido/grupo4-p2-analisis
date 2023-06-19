package uy.edu.um.prog2.adt;

import uy.edu.um.prog2.adt.exceptions.DatosIncorrectos;

public class LinkedListImpl<T> implements LinkedList<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;
    private int lastGetIndex;
    private Node<T> lastGetNode;

    public LinkedListImpl() {
        this.first = null;
        this.size = 0;
    }


    @Override
    public void add(T value) {
        Node<T> elementToAdd = new Node<>(value);

        if (this.first == null)
            this.first = elementToAdd;
        else
            this.last.setNext(elementToAdd);

        this.last = elementToAdd;
        this.size++;
    }
    @Override
    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value); // Crear el nuevo nodo

        if (this.first== null && this.last==null) { // Si la lista está vacía
            first = newNode; // Establecer el nuevo nodo como el primer elemento
            last = newNode; // Establecer el nuevo nodo como el último elemento
        } else {
            newNode.setNext(first); // Establecer el siguiente nodo del nuevo nodo como el nodo actualmente en el primer lugar
            first = newNode; // Establecer el nuevo nodo como el primer elemento
        }

        size++; // Incrementar el tamaño de la lista
    }

    @Override
    public T get(int position) throws DatosIncorrectos {
        if (position < 0 || position >= this.size) {
            throw new DatosIncorrectos();
        }
        int initialPosition = position;
        Node<T> aux;
        if (position == (lastGetIndex + 1)) {
            aux = this.lastGetNode;
            position = 1;
        } else {
            aux = this.first;
        }
        // Se busca el nodo que corresponde con la posición
        for (int i = 0; i < position; i++) {
            aux = aux.getNext();
        }
        // Se retorna el valor del nodo en la posición especificada
        this.lastGetIndex = initialPosition;
        this.lastGetNode = aux;
        return aux.getValue();
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
    public void remove(T value) {

        Node<T> beforeSearchValue = null;
        Node<T> searchValue = this.first;

        // Busco el elemento a eliminar teniendo en cuenta mantener una referencia al elemento anterior
        while (searchValue != null && !searchValue.getValue().equals(value)) {
            beforeSearchValue = searchValue;
            searchValue = searchValue.getNext();
        }

        if (searchValue != null) { // si encontre el elemento a eliminar
            //Verifico si es el primer valor (caso borde) y no es el último
            if (searchValue == this.first && searchValue.getNext() != null) {
                Node<T> aux = this.first;
                this.first = this.first.getNext(); // salteo el primero
                aux.setNext(null); // quito referencia del elemento eliminado al siguiente.
                // Verifico si es el primer valor (caso borde) y no el primero
            } else if (searchValue.getNext() == null && searchValue != this.first) {
                beforeSearchValue.setNext(null);
                this.last = beforeSearchValue;
                // Sí es el primer valor y el último (lista de un solo valor)
            } else if (searchValue.getNext() == null && searchValue == this.first) {
                this.first = null;
                this.last = null;
            } else { // resto de los casos
                beforeSearchValue.setNext(searchValue.getNext());
                searchValue.setNext(null);
            }
            size--;
        }

        // Si no es encuentra el valor a eliminar no se realiza nada
    }

    @Override
    public int size() {
        return this.size;
    }
    @Override
    public boolean isEmpty() {return size == 0;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> currentNode = first;
        while (currentNode != null) {
            sb.append(currentNode.getValue()).append(" -> ");
            currentNode = currentNode.getNext();
        }
        sb.append("null");
        return sb.toString();
    }

}

