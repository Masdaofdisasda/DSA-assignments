package main.java.exercise.result_implementation;

import main.java.exercise.helper.HeuristicType;
import main.java.exercise.helper.InstanceType;

public class AStarResultImplementation extends CommonResultImplementation {

    private int[] path;

    public AStarResultImplementation(InstanceType instanceType, int[] path, HeuristicType heuristicType, int source, int target, String logs) {
        super(instanceType, heuristicType, source, target, logs);
        this.path = path;
    }

    public int[] getPath() {
        return path;
    }
}
