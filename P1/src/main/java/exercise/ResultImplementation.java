package main.java.exercise;

import main.java.framework.PersistAs;
import main.java.framework.Result;

public class ResultImplementation implements Result {

    private String problemType;

    @PersistAs("duration")
    private long duration;

    @PersistAs("size")
    private int size;

    private int solutionIndex;                             // Instanz für StableMatching
    private StableMatchingSolution solutionGaleShapley;    // Lösung für Gale-Shapley
    private boolean solutionStable;                        // Lösung für die Abfrage auf stabiles Matching
    private StableMatchingSolution solutionBruteForce;     // Lösung für Brute Force

    public ResultImplementation(String problemType, long duration, int size, int solutionIndex,
                                StableMatchingSolution solutionGaleShapley, boolean solutionStable, StableMatchingSolution solutionBruteForce) {
        this.problemType = problemType;
        this.size = size;
        this.duration = duration;
        this.solutionIndex = solutionIndex;
        this.solutionGaleShapley = solutionGaleShapley;
        this.solutionStable = solutionStable;
        this.solutionBruteForce = solutionBruteForce;
    }

    public String getProblemType() {
        return problemType;
    }

    public int getSolutionIndex() {
        return solutionIndex;
    }

    public StableMatchingSolution getSolutionGaleShapley() {
        return solutionGaleShapley;
    }

    public boolean getSolutionStable() { return solutionStable; }

    public StableMatchingSolution getSolutionBruteForce() {
        return solutionBruteForce;
    }
}
