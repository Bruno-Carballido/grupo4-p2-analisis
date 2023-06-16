package uy.edu.um.prog2.adt;

public class SimpleNode<T> {

    private T value;

    private SimpleNode<T> next;

    public SimpleNode() {
    }

    public SimpleNode(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public SimpleNode<T> getNext() {
        return next;
    }

    public void setNext(SimpleNode<T> next) {
        this.next = next;
    }
}
