package uy.edu.um.prog2.adt;

import uy.edu.um.prog2.adt.exceptions.EmptyHashException;


public class HashImpl<K extends Comparable<K>, T> implements Hash<K, T> {
    private static final int INITIAL_CAPACITY = 10;
    private static final double LOAD_FACTOR = 0.75;

    private HashNode<K, T>[] array;
    private int size;

    public HashImpl() {
        this.array = new HashNode[INITIAL_CAPACITY];
        this.size = 0;
    }

    private int hashFunction(K key) {
        int hashCode = key.hashCode();
        return hashCode % array.length;
    }

    @Override
    public void put(K key, T value) {
        if (size >= array.length * LOAD_FACTOR)
            resizeArray();

        int index = hashFunction(key);
        HashNode<K, T> newNode = new HashNode<>(key, value);

        if (array[index] == null || !array[index].isActive())
            array[index] = newNode;
        else {
            // En caso de que el lugar en el array ya este tomado busco el siguiente index
            int nextIndex = (index + 1) % array.length;
            while (array[nextIndex] != null && array[nextIndex].isActive()) {
                nextIndex = (nextIndex + 1) % array.length;
            }
            array[nextIndex] = newNode;
        }
        size++;
    }

    @Override
    public T get(K key) throws EmptyHashException {
        int index = hashFunction(key);
        int startIndex = index;

        while (array[index] != null) {
            if (array[index].isActive() && array[index].getKey().equals(key))
                return array[index].getValue();
            index = (index + 1) % array.length;
            if (index == startIndex)
                break;
        }
        throw new EmptyHashException();
    }

    @Override
    public void remove(K key) {
        int index = hashFunction(key);
        int startIndex = index;

        while (array[index] != null) {
            if (array[index].isActive() && array[index].getKey().equals(key)) {
                array[index].setActive(false);
                size--;
                return;
            }
            index = (index + 1) % array.length;
            if (index == startIndex)
                break;
        }
    }

    @Override
    public int size() {
        return size;
    }

    private void resizeArray() {
        HashNode<K, T>[] newArray = new HashNode[array.length * 2];
        for (HashNode<K, T> node : array) {
            if (node != null && node.isActive()) {
                int newIndex = hashFunction(node.getKey());
                while (newArray[newIndex] != null) {
                    newIndex = (newIndex + 1) % newArray.length;
                }
                newArray[newIndex] = node;
            }
        }
        array = newArray;
    }
}
