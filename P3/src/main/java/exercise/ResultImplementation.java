package main.java.exercise;

import main.java.exercise.tree.AVLNode;
import main.java.exercise.tree.Node;
import main.java.framework.PersistAs;
import main.java.framework.Result;

public class ResultImplementation implements Result {

    @PersistAs("numberOfKeys")
    private int numberOfKeys;

    @PersistAs("keyGroup")
    private String keyGroup;

    @PersistAs("averageDurationLookupSimpleSearchTree")
    private long averageDurationLookupSimpleSearchTree;

    @PersistAs("averageDurationLookupAVLTree")
    private long averageDurationLookupAVLTree;

    private int lastKeyNotFoundInSimpleSearchTree;
    private int lastKeyNotFoundInAVLTree;

    private Node root;
    private AVLNode avlPseudoRoot;

    @PersistAs("simpleSearchTree")
    private String simpleSearchTree = "";

    @PersistAs("avlTree")
    private String avlTree = "";

    public ResultImplementation(
            int numberOfKeys,
            String keyGroup,
            long averageDurationLookupSimpleSearchTree,
            long averageDurationLookupAVLTree,
            int lastKeyNotFoundInSimpleSearchTree,
            int lastKeyNotFoundInAVLTree,
            Node root,
            AVLNode avlPseudoRoot,
            int[] simpleBinaryTreeArray,
            int[] avlTreeArray
    ) {
        this.numberOfKeys = numberOfKeys;
        this.keyGroup = keyGroup;
        this.averageDurationLookupSimpleSearchTree = averageDurationLookupSimpleSearchTree;
        this.averageDurationLookupAVLTree = averageDurationLookupAVLTree;
        this.lastKeyNotFoundInSimpleSearchTree = lastKeyNotFoundInSimpleSearchTree;
        this.lastKeyNotFoundInAVLTree = lastKeyNotFoundInAVLTree;
        this.root = root;
        this.avlPseudoRoot = avlPseudoRoot;

        if (simpleBinaryTreeArray != null) {
            String simpleBinaryTree = "";
            for (int i = 0; i < simpleBinaryTreeArray.length; i++) {
                if (i != 0) {
                    simpleBinaryTree += "|";
                }
                simpleBinaryTree += simpleBinaryTreeArray[i];
            }
            this.simpleSearchTree = simpleBinaryTree;
        }

        if (avlTreeArray != null) {
            String avlTree = "";
            for (int i = 0; i < avlTreeArray.length; i++) {
                if (i != 0) {
                    avlTree += "|";
                }
                avlTree += avlTreeArray[i];
            }
            this.avlTree = avlTree;
        }
    }

    public int getLastKeyNotFoundInSimpleSearchTree() {
        return lastKeyNotFoundInSimpleSearchTree;
    }

    public int getLastKeyNotFoundInAVLTree() {
        return lastKeyNotFoundInAVLTree;
    }

    public Node getRoot() {
        return root;
    }

    public AVLNode getAVLPseudoRoot() {
        return avlPseudoRoot;
    }
}
