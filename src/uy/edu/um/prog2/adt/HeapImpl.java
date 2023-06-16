package uy.edu.um.prog2.adt;


import uy.edu.um.prog2.adt.exceptions.EmptyHeapException;

public class HeapImpl<K extends Comparable<K>, T> implements Heap<K, T> {

    private static final int HEAP_LENGTH = 10;

    private HeapNode[] heap;
    private int size;

    public HeapImpl() {
        heap = new HeapNode[HEAP_LENGTH];
        size = 0;
    }

    @Override
    public void insert(K key, T value) {
        HeapNode<K, T> node = new HeapNode<>(key, value);
        if (size == heap.length)
            increaseSize();
        heap[size] = node;
        moveNodeUp(size);
        size++;
    }

    private void moveNodeUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap[index].getKey().compareTo(heap[parentIndex].getKey()) <= 0)
                break;
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    private void swap(int i, int j) {
        HeapNode<K, T> temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void increaseSize() {
        HeapNode<K, T>[] newHeap = new HeapNode[size * 2];
        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    @Override
    public void delete() throws EmptyHeapException {
        if (size() == 0)
            throw new EmptyHeapException();
        swap(0, size - 1);
        heap[size - 1] = null;
        size--;
        moveNodeDown(0);
    }


    @Override
    public T getMaxValue() {
        if (size == 0)
            return null;
        return (T) heap[0].getValue();
    }

    @Override
    public int size() {
        return size;
    }


    private void moveNodeDown(int index) {
        int maxIndex = index;
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;

        // Si no llegue al final && el nodo izquierdo es más grande
        if (leftChildIndex < size && heap[leftChildIndex].getKey().compareTo(heap[maxIndex].getKey()) > 0)
            maxIndex = leftChildIndex;
        // Si no llegue al final && el nodo derecho es más grande (aca puede ser que tenga en maxIndex el nodo izquierdo o el original, de esta manera me aseguro que el más grande de los 3 siempre queda arriba)
        if (rightChildIndex < size && heap[rightChildIndex].getKey().compareTo(heap[maxIndex].getKey()) > 0)
            maxIndex = rightChildIndex;

        if (index != maxIndex) {
            swap(index, maxIndex);
            moveNodeDown(maxIndex);
        }
    }


}