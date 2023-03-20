package main.java.exercise.result_implementation;

import main.java.exercise.helper.HeuristicType;
import main.java.framework.PersistAs;
import main.java.framework.Result;
import main.java.exercise.helper.InstanceType;

public class CommonResultImplementation implements Result {

    private InstanceType instanceType;

    @PersistAs("heuristicType")
    private HeuristicType heuristicType;

    @PersistAs("source")
    private int source;

    @PersistAs("target")
    private int target;

    @PersistAs("logs")
    private String logs;

    public CommonResultImplementation(InstanceType instanceType) {
        this.instanceType = instanceType;
    }

    public CommonResultImplementation(InstanceType instanceType, HeuristicType heuristicType, int source, int target, String logs) {
        this.instanceType = instanceType;
        this.heuristicType = heuristicType;
        this.source = source;
        this.target = target;
        this.logs = logs;
    }

    public InstanceType getInstanceType() {
        return instanceType;
    }
}
