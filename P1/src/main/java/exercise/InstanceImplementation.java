package main.java.exercise;

import main.java.framework.Instance;

public class InstanceImplementation implements Instance {

    private String groupName;                              // Art der Instanz
    private int number;                                    // Nummer der Instanz
    private int[] numbers;                                 // Zahlen für Teilaufgabe Indexsuche
    private int element;                                   // Das Element, dessen Index wir suchen
    private int solutionIndex;                             // Die Lösung für die Indexsuche - der gesuchte Index
    private StableMatchingInstance stableMatchingInstance; // Instanz für StableMatching
    private StableMatchingSolution solutionGaleShapley;    // Lösung für Gale-Shapley
    private StableMatchingSolution solutionStableCandit;   // Lösungsvorschlag für Abfrage auf stabiles Matching
    private boolean solutionStable;                        // Lösung für Abfrage auf stabiles Matching
    private StableMatchingSolution solutionBruteForce;     // Lösung für Brute Force

    public InstanceImplementation(String groupName, int number, int[] numbers, int element, int solutionIndex,
                                  StableMatchingInstance stableMatchingInstance,
                                  StableMatchingSolution solutionGaleShapley, StableMatchingSolution solutionStableCandit,
                                  boolean solutionStable, StableMatchingSolution solutionBruteForce) {
        this.groupName = groupName;
        this.number = number;
        this.numbers = numbers;
        this.element = element;
        this.solutionIndex = solutionIndex;
        this.stableMatchingInstance = stableMatchingInstance;
        this.solutionGaleShapley = solutionGaleShapley;
        this.solutionStableCandit = solutionStableCandit;
        this.solutionStable = solutionStable;
        this.solutionBruteForce = solutionBruteForce;
    }

    @Override
    public String getGroupName() {
        return this.groupName;
    }

    @Override
    public int getNumber() {
        return this.number;
    }

    public int[] getNumbers() {
        return this.numbers;
    }

    public int getElement() {
        return this.element;
    }

    public int getSolutionIndex() {
        return this.solutionIndex;
    }

    public StableMatchingInstance getStableMatchingInstance() {
        return this.stableMatchingInstance;
    }

    public StableMatchingSolution getSolutionGaleShapley() {
        return this.solutionGaleShapley;
    }

    public StableMatchingSolution getSolutionStableCandit() { return this.solutionStableCandit; }

    public boolean getSolutionStable() { return this.solutionStable; }

    public StableMatchingSolution getSolutionBruteForce() {
        return this.solutionBruteForce;
    }
}
