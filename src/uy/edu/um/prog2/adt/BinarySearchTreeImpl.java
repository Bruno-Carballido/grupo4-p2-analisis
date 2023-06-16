package uy.edu.um.prog2.adt;


import uy.edu.um.prog2.adt.exceptions.EmptyTreeException;

public class BinarySearchTreeImpl<K extends Comparable<K>, T> implements BinarySearchTree<K, T> {

    private BinarySearchTreeNode<K, T> root;

    public BinarySearchTreeImpl() {
        root = null;
    }

    @Override
    public boolean find(K key) {
        return findRecursive(root, key);
    }

    private boolean findRecursive(BinarySearchTreeNode<K, T> node, K key) {
        if (node == null)
            return false;

        int compare = key.compareTo(node.getKey());

        if (compare == 0)
            return true;
        else if (compare < 0)
            return findRecursive(node.getLeft(), key);
        else
            return findRecursive(node.getRight(), key);
    }

    @Override
    public void insert(K key, T value) {
        root = insertRecursive(root, key, value);
    }

    private BinarySearchTreeNode<K, T> insertRecursive(BinarySearchTreeNode<K, T> node, K key, T value) {
        if (node == null)
            return new BinarySearchTreeNode<>(key, value);

        int compare = key.compareTo(node.getKey());

        if (compare < 0)
            node.setLeft(insertRecursive(node.getLeft(), key, value));
        else if (compare > 0)
            node.setRight(insertRecursive(node.getRight(), key, value));
        else
            node.setValue(value);

        return node;
    }

    @Override
    public void delete(K key) throws EmptyTreeException {
        if (root == null)
            throw new EmptyTreeException();
        root = deleteRecursive(root, key);
    }

    private BinarySearchTreeNode<K, T> deleteRecursive(BinarySearchTreeNode<K, T> node, K key) {
        if (node == null)
            return null;

        int compare = key.compareTo(node.getKey());

        if (compare < 0)
            node.setLeft(deleteRecursive(node.getLeft(), key));
        else if (compare > 0)
            node.setRight(deleteRecursive(node.getRight(), key));
        else {
            if (node.getLeft() == null && node.getRight() == null)
                node = null; // Si el Nodo es Hoja retorno null para que el padre pierda la referencia.
            else if (node.getLeft() == null)
                node = node.getRight(); // Si el Nodo tiene un solo hijo del lado derecho lo retorno para que el padre apunte a él
            else if (node.getRight() == null)
                node = node.getLeft(); // Si el Nodo tiene un solo hijo del lado izquierdo lo retorno para que el padre apunte a él
            else {
                // En caso de que el Nodo a eliminar tenga ambos hijos; busco el menor nodo del lado derecho y lo utilizo para remplazar el nodo a eliminar
                BinarySearchTreeNode<K, T> minValueNode = findMinValueNode(node.getRight());
                node.setKey(minValueNode.getKey());
                node.setValue(minValueNode.getValue());
                node.setRight(deleteRecursive(node.getRight(), minValueNode.getKey())); // Seteo a la derecha el sub arbol derecho, pero sin el nodo que subí para remplazar el eliminado
            }
        }

        return node;
    }

    @Override
    public T findMin() {
        if (root == null) {
            return null;
        }
        return findMinValueNode(root).getValue();
    }

    private BinarySearchTreeNode<K, T> findMinValueNode(BinarySearchTreeNode<K, T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    @Override
    public T findMax() {
        if (root == null) {
            return null;
        }
        return findMaxValueNode(root).getValue();
    }

    private BinarySearchTreeNode<K, T> findMaxValueNode(BinarySearchTreeNode<K, T> node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }
}