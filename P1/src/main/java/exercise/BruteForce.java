package main.java.exercise;

public class BruteForce {

    public static StableMatchingSolution performBruteForce(StableMatchingInstance instance, StudentSolutionImplementation studentSolutionImplementation) {
        int[] permutation = new int[instance.getN()];
        for (int i = 0; i < permutation.length; i++) {
            permutation[i] = i;
        }

        PermutationSwaps permutationSwaps = new PermutationSwaps(instance.getN());

        StableMatchingSolution solution = new StableMatchingSolution(instance, studentSolutionImplementation);
        for (int i = 0; i < instance.getN(); i++) {
            solution.assign(i, permutation[i]);
        }

        while (permutationSwaps.hasNext()) {
            SwapElement e = permutationSwaps.nextSwap();

            // Tauschen
            solution.swap(e.getI(), e.getJ());

            // Prüfen, ob stabil
            boolean res = studentSolutionImplementation.isStableMatching(instance, solution);
            if (res) {
                return solution;
            }
        }

        return null;
    }

}
