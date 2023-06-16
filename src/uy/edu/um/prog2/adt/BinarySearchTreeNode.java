package uy.edu.um.prog2.adt;

public class BinarySearchTreeNode<K extends Comparable<K>, T> {

    private K key;
    private T value;
    private BinarySearchTreeNode<K, T> left;
    private BinarySearchTreeNode<K, T> right;

    public BinarySearchTreeNode() {
    }

    public BinarySearchTreeNode(K key, T value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public BinarySearchTreeNode<K, T> getLeft() {
        return left;
    }

    public void setLeft(BinarySearchTreeNode<K, T> left) {
        this.left = left;
    }

    public BinarySearchTreeNode<K, T> getRight() {
        return right;
    }

    public void setRight(BinarySearchTreeNode<K, T> right) {
        this.right = right;
    }
}
