package main.java.exercise;

public class PermutationSwaps {

    private int n;
    private java.util.LinkedList<SwapElement> queue;

    public PermutationSwaps(int n) {
        this.n = n;
        queue = new java.util.LinkedList<SwapElement>();
        queue.add(new SwapElement(0, 0));
        generateSwaps(n, n);
    }

    public boolean hasNext() {
        return !queue.isEmpty();
    }

    public SwapElement nextSwap() {
        return queue.pop();
    }

    private void generateSwaps(int k, int n) {
        if (k > 1) {
            for (int i = 0; i < k; i++) {
                generateSwaps(k-1, n);
                if (i < k - 1) {
                    if (k % 2 == 0) {
                        queue.addLast(new SwapElement(i, k-1));
                    }
                    else {
                        queue.addLast(new SwapElement(0, k-1));
                    }
                }
            }
        }
    }
}


