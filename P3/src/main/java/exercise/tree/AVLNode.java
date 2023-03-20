package main.java.exercise.tree;

public class AVLNode {

    private AVLNode parent;
    private AVLNode leftChild;
    private AVLNode rightChild;
    private int key;
    private int height;

    public static int height(AVLNode node) {
        if (node == null) {
            return -1;
        } else {
            return node.height;
        }
    }

    private static void setParent(AVLNode node, AVLNode parent) {
        if (node != null) {
            node.parent = parent;
        }
    }

    public AVLNode(int key) {
        this.key = key;
    }

    public AVLNode getLeftChild() {
        return leftChild;
    }

    public AVLNode getRightChild() {
        return rightChild;
    }

    public int getKey() {
        return key;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void attachNodeLeft(AVLNode node) {
        if (this.leftChild == null) {
            this.leftChild = node;
            node.parent = this;
        }
    }

    public void attachNodeRight(AVLNode node) {
        if (this.rightChild == null) {
            this.rightChild = node;
            node.parent = this;
        }
    }

    public void rotateToRight() {
        AVLNode u = this;
        AVLNode parentOfSubtree = u.parent;
        boolean uIsLeftChild = parentOfSubtree.leftChild != null && parentOfSubtree.leftChild.key == u.key;
        AVLNode v = u.leftChild;

        u.leftChild = v.rightChild;
        AVLNode.setParent(u.leftChild, u);

        v.rightChild = u;
        AVLNode.setParent(v.rightChild, v);

        if (uIsLeftChild) {
            parentOfSubtree.leftChild = v;
        } else {
            parentOfSubtree.rightChild = v;
        }
        AVLNode.setParent(v, parentOfSubtree);

        u.height = Math.max(AVLNode.height(u.leftChild), AVLNode.height(u.rightChild)) + 1;
        v.height = Math.max(AVLNode.height(v.leftChild), AVLNode.height(u)) + 1;
    }

    public void rotateToLeft() {
        AVLNode u = this;
        AVLNode parentOfSubtree = u.parent;
        boolean uIsLeftChild = parentOfSubtree.leftChild != null && parentOfSubtree.leftChild.key == u.key;
        AVLNode v = u.rightChild;

        u.rightChild = v.leftChild;
        AVLNode.setParent(u.rightChild, u);

        v.leftChild = u;
        AVLNode.setParent(v.leftChild, v);

        if (uIsLeftChild) {
            parentOfSubtree.leftChild = v;
        } else {
            parentOfSubtree.rightChild = v;
        }
        AVLNode.setParent(v, parentOfSubtree);

        u.height = Math.max(AVLNode.height(u.leftChild), AVLNode.height(u.rightChild)) + 1;
        v.height = Math.max(AVLNode.height(u), AVLNode.height(v.rightChild)) + 1;
    }

    public void doubleRotateLeftRight() {
        AVLNode u = this;
        u.leftChild.rotateToLeft();
        u.rotateToRight();
    }

    public void doubleRotateRightLeft() {
        AVLNode u = this;
        u.rightChild.rotateToRight();
        u.rotateToLeft();
    }
}
