package main.java.exercise.result_implementation;

import main.java.exercise.helper.InstanceType;

public class HeuristicsResultImplementation extends CommonResultImplementation {

    private double result;

    public HeuristicsResultImplementation(InstanceType instanceType, double result) {
        super(instanceType);
        this.result = result;
    }

    public double getResult() {
        return result;
    }
}
