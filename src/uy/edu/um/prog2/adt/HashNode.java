package uy.edu.um.prog2.adt;

public class HashNode <K extends Comparable<K>, T>{

    private K key;
    private T value;
    private boolean isActive;

    public HashNode() {
    }

    public HashNode(K key, T value) {
        this.key = key;
        this.value = value;
        this.isActive = true;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
