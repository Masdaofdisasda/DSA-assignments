package main.java.exercise;

import main.java.exercise.tree.AVLNode;
import main.java.exercise.tree.Node;
import main.java.framework.StudentInformation;
import main.java.framework.StudentSolution;

public class StudentSolutionImplementation implements StudentSolution {
    @Override
    public StudentInformation provideStudentInformation() {
        return new StudentInformation(
                "David", // Vorname
                "Köppl", // Nachname
                "12022493" // Matrikelnummer
        );
    }

    // Implementieren Sie hier Ihre Lösung für das Einfügen in einen einfachen binären Suchbaum
    public void insertSimpleBinarySearchTree(Node root, Node newNode) {

        if (root == null) {
            root = newNode;
            return;
        } else {
            Node current = root;
            Node parent;
            int key = newNode.getKey();

            while (true) {
                parent = current;

                if (key < current.getKey()) {
                    current = current.getLeftChild();

                    if (current == null) {
                        parent.attachNodeLeft(newNode);
                        return;
                    }
                } else if (key > current.getKey()) {
                    current = current.getRightChild();

                    if (current == null) {
                        parent.attachNodeRight(newNode);
                        return;
                    }
                } else return;
            }
        }

    }

    // Implementieren Sie hier Ihre Lösung für das Einfügen in einen AVL-Baum
    public void insertAVLTree(AVLNode p, AVLNode newNode) {
        if (p == null) {
            p = newNode;
            p.setHeight(0);
        } else {
            if (newNode.getKey() < p.getKey()) {
                if (p.getLeftChild() == null){
                    p.attachNodeLeft(newNode);
                    p.getLeftChild().setHeight(0);
                }else {
                    insertAVLTree(p.getLeftChild(), newNode);
                }
                if (AVLNode.height(p.getRightChild()) - AVLNode.height(p.getLeftChild()) == -2) {
                    if (AVLNode.height(p.getLeftChild().getLeftChild()) >= AVLNode.height(p.getLeftChild().getRightChild())){
                        p.rotateToRight();
                    }else {
                        p. doubleRotateLeftRight();
                    }
                }
            } else if (newNode.getKey() > p.getKey()) {
                if (p.getRightChild() == null){
                    p.attachNodeRight(newNode);
                    p.getRightChild().setHeight(0);
                }else {
                    insertAVLTree(p.getRightChild(), newNode);
                }
                if (AVLNode.height(p.getRightChild()) - AVLNode.height(p.getLeftChild()) == 2) {
                    if (AVLNode.height(p.getRightChild().getRightChild()) >= AVLNode.height(p.getRightChild().getLeftChild())){
                        p.rotateToLeft();
                    }else {
                        p.doubleRotateRightLeft();
                    }
                }
            } else return;
        }
        p.setHeight(1 + Math.max(AVLNode.height(p.getLeftChild()), AVLNode.height(p.getRightChild())));

//        if (p == null) {
//            p = newNode;
//            updateHeight(p);
//            return;
//        } else {
//            AVLNode current = p;
//            AVLNode parent;
//            int key = newNode.getKey();
//
//            while (true) {
//                parent = current;
//
//                if (key < current.getKey()) {
//                    current = current.getLeftChild();
//
//                    if (current == null) {
//                        parent.attachNodeLeft(newNode);
//                        break;
//                    }
//                } else if (key > current.getKey()) {
//                    current = current.getRightChild();
//
//                    if (current == null) {
//                        parent.attachNodeRight(newNode);
//                        break;
//                    }
//                } else return;
//
//            }
//            updateHeight(p);
//            balance(p);
    }

    public int updateHeight(AVLNode n) {
        int height = 0;

        if (n.getLeftChild() == null && n.getRightChild() == null) {
            n.setHeight(height);
            return height;
        }
        if (n.getLeftChild() != null && n.getRightChild() == null) {
            height = 1 + updateHeight(n.getLeftChild());
            n.setHeight(height);
            return height;
        }
        if (n.getLeftChild() == null && n.getRightChild() != null) {
            height = 1 + updateHeight(n.getRightChild());
            n.setHeight(height);
            return height;
        } else {
            height = 1 + Math.max(updateHeight(n.getLeftChild()), updateHeight(n.getRightChild()));
            n.setHeight(height);
            return height;
        }
    }

    public int BalanceOf(AVLNode n) {
        if (n == null) {
            return 0;
        }

        return AVLNode.height(n.getRightChild()) - AVLNode.height(n.getLeftChild());
    }

    public void balance(AVLNode n) {

        if (n == null) {
            return;
        }

//        AVLNode parent = n;
//        while (!(Math.abs(BalanceOf(n)) > 1) || n != null) {
//            parent = n;
//            if (BalanceOf(n) > 0) {
//                n = n.getRightChild();
//            } else if (BalanceOf(n) < 0) {
//                n = n.getLeftChild();
//            }
//        }
//        n = parent;


        int bal = BalanceOf(n);

        if (bal <= -2) {
            if (BalanceOf(n.getLeftChild()) == 1) {
                n.doubleRotateLeftRight();
                return;
            } else if (BalanceOf(n.getLeftChild()) == 0 || BalanceOf(n.getLeftChild()) == -1) {
                n.rotateToRight();
                return;
            } else {
                balance(n.getLeftChild());
            }
        } else if (bal >= 2) {
            if (BalanceOf(n.getRightChild()) == -1) {
                n.doubleRotateRightLeft();
                return;
            } else if (BalanceOf(n.getRightChild()) == 0 || BalanceOf(n.getRightChild()) == 1) {
                n.rotateToLeft();
                return;
            } else {
                balance(n.getRightChild());
            }
        } else {
            balance(n.getRightChild());
            balance(n.getLeftChild());
        }
    }

}
