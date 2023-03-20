package main.java.exercise;

import main.java.exercise.tree.AVLNode;
import main.java.exercise.tree.Node;
import main.java.framework.*;

import java.util.Stack;

public class VerifierImplementation extends Verifier<InstanceImplementation, StudentSolutionImplementation, ResultImplementation> {

    @Override
    public ResultImplementation solveProblemUsingStudentSolution(InstanceImplementation instance, StudentSolutionImplementation studentSolution) {
        int[] keys = instance.getKeys();
        Node root = new Node(keys[0]);
        AVLNode avlPseudoRoot = new AVLNode(Integer.MIN_VALUE);
        AVLNode avlRoot = new AVLNode(keys[0]);
        avlPseudoRoot.attachNodeRight(avlRoot);
        int start = 1;
        if (instance.getKeyGroup().startsWith("debug ")) {
            for (int i = start; i < keys.length - 1 && i < instance.getNumberOfKeys(); i++) {
                studentSolution.insertAVLTree(avlPseudoRoot.getRightChild(), new AVLNode(keys[i]));
                start++;
            }
        }
        for (int i = start; i < keys.length && i < instance.getNumberOfKeys(); i++) {
            if (!instance.isTestOnlyAVL() && !instance.getKeyGroup().startsWith("debug ")) {
                studentSolution.insertSimpleBinarySearchTree(root, new Node(keys[i]));
            }
            if (!instance.isTestOnlySimple()) {
                studentSolution.insertAVLTree(avlPseudoRoot.getRightChild(), new AVLNode(keys[i]));
            }
            if (i % 100 == 0) {
                if (!instance.isTestOnlyAVL() && !instance.getKeyGroup().startsWith("debug ")) {
                    studentSolution.insertSimpleBinarySearchTree(root, new Node(keys[i]));
                }
                if (!instance.isTestOnlySimple()) {
                    studentSolution.insertAVLTree(avlPseudoRoot.getRightChild(), new AVLNode(keys[i]));
                }
            }
        }

        long averageDurationLookupSimpleSearchTree = 0;
        long averageDurationLookupAVLTree = 0;
        for (int i = 0; i < keys.length && i < instance.getNumberOfKeys(); i++) {
            Timer timerLookupSimpleSearchTree = new Timer();
            timerLookupSimpleSearchTree.start();
            boolean keyFoundInSimpleSearchTree = this.lookupKey(root, keys[i]);
            timerLookupSimpleSearchTree.stop();

            Timer timerLookupAVLTree = new Timer();
            timerLookupAVLTree.start();
            boolean keyFoundInAVLTree = this.lookupKeyAVLTree(avlPseudoRoot.getRightChild(), keys[i]);
            timerLookupAVLTree.stop();

            long timeSimpleSearchTreeSum = averageDurationLookupSimpleSearchTree * i;
            timeSimpleSearchTreeSum += timerLookupSimpleSearchTree.getDuration();
            averageDurationLookupSimpleSearchTree = timeSimpleSearchTreeSum / (i + 1);

            long timeAVLTreeSum = averageDurationLookupAVLTree * i;
            timeAVLTreeSum += timerLookupAVLTree.getDuration();
            averageDurationLookupAVLTree = timeAVLTreeSum / (i + 1);

            int lastKeyNotFoundInSimpleSearchTree = Integer.MIN_VALUE;
            int lastKeyNotFoundInAVLTree = Integer.MIN_VALUE;

            if (!keyFoundInSimpleSearchTree) {
                lastKeyNotFoundInSimpleSearchTree = keys[i];
            }
            if (!keyFoundInAVLTree) {
                lastKeyNotFoundInAVLTree = keys[i];
            }
            if (!instance.isTestOnlySimple() && !instance.isTestOnlyAVL() && !instance.getKeyGroup().startsWith("debug ")) {
                if (!keyFoundInSimpleSearchTree || !keyFoundInAVLTree) {
                    return new ResultImplementation(
                            instance.getNumberOfKeys(),
                            instance.getKeyGroup(),
                            averageDurationLookupSimpleSearchTree,
                            averageDurationLookupAVLTree,
                            lastKeyNotFoundInSimpleSearchTree,
                            lastKeyNotFoundInAVLTree,
                            root,
                            avlPseudoRoot,
                            null,
                            null);
                }
            } else {
                if (instance.isTestOnlySimple() && !keyFoundInSimpleSearchTree) {
                    return new ResultImplementation(
                            instance.getNumberOfKeys(),
                            instance.getKeyGroup(),
                            averageDurationLookupSimpleSearchTree,
                            averageDurationLookupAVLTree,
                            lastKeyNotFoundInSimpleSearchTree,
                            lastKeyNotFoundInAVLTree,
                            root,
                            avlPseudoRoot,
                            null,
                            null);
                }
                if (instance.isTestOnlyAVL() && !keyFoundInAVLTree) {
                    return new ResultImplementation(
                            instance.getNumberOfKeys(),
                            instance.getKeyGroup(),
                            averageDurationLookupSimpleSearchTree,
                            averageDurationLookupAVLTree,
                            lastKeyNotFoundInSimpleSearchTree,
                            lastKeyNotFoundInAVLTree,
                            root,
                            avlPseudoRoot,
                            null,
                            null);
                }
            }
        }
        int[] simpleSearchTreeArray = null;
        int[] avlTreeArray = null;
        if (instance.getNumberOfKeys() <= 10) {
            simpleSearchTreeArray = new int[1023];
            this.mapTreeToArray(root, 0, simpleSearchTreeArray);
            avlTreeArray = new int[1023];
            this.mapTreeToArrayAVLTree(avlPseudoRoot.getRightChild(), 0, avlTreeArray);
        }
        return new ResultImplementation(
                instance.getNumberOfKeys(),
                instance.getKeyGroup(),
                averageDurationLookupSimpleSearchTree,
                averageDurationLookupAVLTree,
                Integer.MIN_VALUE,
                Integer.MIN_VALUE,
                root,
                avlPseudoRoot,
                simpleSearchTreeArray,
                avlTreeArray);
    }

    private boolean lookupKey(Node root, int key) {
        Node p = root;
        while (p != null && p.getKey() != key) {
            if (key < p.getKey()) {
                p = p.getLeftChild();
            } else {
                p = p.getRightChild();
            }
        }
        if (p != null) {
            return true;
        } else {
            return false;
        }
    }

    private boolean lookupKeyAVLTree(AVLNode node, int key) {
        if (key == node.getKey()) {
            return true;
        } else if (key < node.getKey()) {
            if (node.getLeftChild() != null) {
                return this.lookupKeyAVLTree(node.getLeftChild(), key);
            } else {
                return false;
            }
        } else {
            if (node.getRightChild() != null) {
                return this.lookupKeyAVLTree(node.getRightChild(), key);
            } else {
                return false;
            }
        }
    }

    private void mapTreeToArray(Node node, int index, int[] array) {
        if (index < array.length) {
            array[index] = node.getKey();
            if (node.getLeftChild() != null) {
                this.mapTreeToArray(node.getLeftChild(), 2 * index + 1, array);
            }
            if (node.getRightChild() != null) {
                this.mapTreeToArray(node.getRightChild(), 2 * index + 2, array);
            }
        }
    }

    private void mapTreeToArrayAVLTree(AVLNode node, int index, int[] array) {
        if (index < array.length) {
            array[index] = node.getKey();
            if (node.getLeftChild() != null) {
                this.mapTreeToArrayAVLTree(node.getLeftChild(), 2 * index + 1, array);
            }
            if (node.getRightChild() != null) {
                this.mapTreeToArrayAVLTree(node.getRightChild(), 2 * index + 2, array);
            }
        }
    }

    @Override
    public Report verifyResult(InstanceImplementation instance, ResultImplementation result) {
        if (instance.getKeyGroup().startsWith("debug ")) {
            String debugType = instance.getKeyGroup().split(" ")[1];
            String additionalMessage = "";
            switch (debugType) {
                case "right":
                    additionalMessage = " See case 1.1 in the slides.";
                    break;
                case "left-right":
                    additionalMessage = " See case 1.2 in the slides.";
                    break;
                case "left":
                    additionalMessage = " See case 2.1 in the slides.";
                    break;
                case "right-left":
                default:
                    additionalMessage = " See case 2.2 in the slides.";
                    break;
            }
            if (result.getLastKeyNotFoundInAVLTree() != Integer.MIN_VALUE) {
                return new Report(false, "Error in instance " + instance.getNumber() + ": Key '" + result.getLastKeyNotFoundInAVLTree() + "' expected to be found in the AVL tree but was not." + additionalMessage);
            }
            int sizeAVLTree = this.sizeAVLTree(result.getAVLPseudoRoot().getRightChild());
            if (sizeAVLTree != instance.getNumberOfKeys()) {
                return new Report(false, "Error in instance " + instance.getNumber() + ": Size of the AVL tree expected to be " + instance.getNumberOfKeys() + " but was " + sizeAVLTree + "." + additionalMessage);
            }
            if (!this.isBalanced(result.getAVLPseudoRoot().getRightChild())) {
                return new Report(false, "Error in instance " + instance.getNumber() + ": Rotation \"" + debugType + "\" was expected." + additionalMessage);
            }
            return new Report(true, "");
        }
        if (!(result.getLastKeyNotFoundInSimpleSearchTree() == Integer.MIN_VALUE && result.getLastKeyNotFoundInAVLTree() == Integer.MIN_VALUE)) {
            if (instance.isTestOnlySimple()) {
                if (result.getLastKeyNotFoundInSimpleSearchTree() != Integer.MIN_VALUE) {
                    return new Report(false, "Error in instance " + instance.getNumber() + ": Key '" + result.getLastKeyNotFoundInSimpleSearchTree() + "' expected to be found in the simple binary tree but was not.");
                }
            } else if (instance.isTestOnlyAVL()) {
                if (result.getLastKeyNotFoundInAVLTree() != Integer.MIN_VALUE) {
                    return new Report(false, "Error in instance " + instance.getNumber() + ": Key '" + result.getLastKeyNotFoundInAVLTree() + "' expected to be found in the AVL tree but was not.");
                }
            } else {
                return this.createMissingKeyReport(instance, result);
            }
        }

        int size = this.size(result.getRoot());
        int sizeAVLTree = this.sizeAVLTree(result.getAVLPseudoRoot().getRightChild());

        if (!(size == instance.getNumberOfKeys() && sizeAVLTree == instance.getNumberOfKeys())) {
            if (instance.isTestOnlySimple()) {
                if (size != instance.getNumberOfKeys()) {
                    return new Report(false, "Error in instance " + instance.getNumber() + ": Size of the simple binary tree expected to be " + instance.getNumberOfKeys() + " but was " + size + ".");
                }
            } else if (instance.isTestOnlyAVL()) {
                if (sizeAVLTree != instance.getNumberOfKeys()) {
                    return new Report(false, "Error in instance " + instance.getNumber() + ": Size of the AVL tree expected to be " + instance.getNumberOfKeys() + " but was " + sizeAVLTree + ".");
                }
            } else {
                return this.createWrongSizeReport(instance, size, sizeAVLTree);
            }
        }

        if (!this.isBalanced(result.getAVLPseudoRoot().getRightChild())) {
            if (!instance.isTestOnlySimple()) {
                return new Report(false, "Error in instance " + instance.getNumber() + ": Not every node in the created AVL tree is balanced.");
            }
        }

        return new Report(true, "");
    }

    private Report createMissingKeyReport(InstanceImplementation instance, ResultImplementation result) {
        if (result.getLastKeyNotFoundInSimpleSearchTree() != Integer.MIN_VALUE) {
            return new Report(false, "Error in instance " + instance.getNumber() + ": Key '" + result.getLastKeyNotFoundInSimpleSearchTree() + "' expected to be found in the simple binary tree but was not.");
        } else {
            return new Report(false, "Error in instance " + instance.getNumber() + ": Key '" + result.getLastKeyNotFoundInAVLTree() + "' expected to be found in the AVL tree but was not.");
        }
    }

    private Report createWrongSizeReport(InstanceImplementation instance, int size, int sizeAVLTree) {
        if (size != instance.getNumberOfKeys()) {
            return new Report(false, "Error in instance " + instance.getNumber() + ": Size of the simple binary tree expected to be " + instance.getNumberOfKeys() + " but was " + size + ".");
        } else {
            return new Report(false, "Error in instance " + instance.getNumber() + ": Size of the AVL tree expected to be " + instance.getNumberOfKeys() + " but was " + sizeAVLTree + ".");
        }
    }

    private int size(Node root) {
        Stack<Node> stack = new Stack<Node>();
        stack.push(root);
        int size = 0;

        while (stack.size() > 0) {
            Node node = stack.pop();
            size += 1;

            if (node.getLeftChild() != null) {
                stack.push(node.getLeftChild());
            }
            if (node.getRightChild() != null) {
                stack.push(node.getRightChild());
            }
        }
        return size;
    }

    private int sizeAVLTree(AVLNode root) {
        Stack<AVLNode> stack = new Stack<AVLNode>();
        stack.push(root);
        int size = 0;

        while (stack.size() > 0) {
            AVLNode node = stack.pop();
            size += 1;

            if (node.getLeftChild() != null) {
                stack.push(node.getLeftChild());
            }
            if (node.getRightChild() != null) {
                stack.push(node.getRightChild());
            }
        }
        return size;
    }

    private boolean isBalanced(AVLNode node) {
        boolean isBalanced = true;
        boolean leftIsBalanced = true;
        boolean rightIsBalanced = true;
        if (node.getLeftChild() != null) {
            leftIsBalanced = this.isBalanced(node.getLeftChild());
        }
        if (node.getRightChild() != null) {
            rightIsBalanced = this.isBalanced(node.getRightChild());
        }
        isBalanced = leftIsBalanced
                && rightIsBalanced
                && Math.abs(AVLNode.height(node.getRightChild()) - AVLNode.height(node.getLeftChild())) <= 1;
        node.setHeight(Math.max(AVLNode.height(node.getLeftChild()), AVLNode.height(node.getRightChild())) + 1);
        return isBalanced;
    }
}
