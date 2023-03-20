package main.java.exercise.tree;

public class Node {

    private Node leftChild;
    private Node rightChild;
    private int key;

    public Node(int key) {
        this.key = key;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public int getKey() {
        return key;
    }

    public void attachNodeLeft(Node node) {
        if (this.leftChild == null) {
            this.leftChild = node;
        }
    }

    public void attachNodeRight(Node node) {
        if (this.rightChild == null) {
            this.rightChild = node;
        }
    }
}
