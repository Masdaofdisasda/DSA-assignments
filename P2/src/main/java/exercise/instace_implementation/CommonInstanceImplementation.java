package main.java.exercise.instace_implementation;
import main.java.exercise.helper.InstanceType;
import main.java.framework.Instance;

public class CommonInstanceImplementation implements Instance {

    private String groupName;

    private int number;

    private InstanceType instanceType;

    public CommonInstanceImplementation(String groupName, int number, InstanceType instanceType) {
        this.groupName = groupName;
        this.number = number;
        this.instanceType = instanceType;
    }

    @Override
    public String getGroupName() {
        return this.groupName;
    }

    @Override
    public int getNumber() {
        return this.number;
    }

    public InstanceType getInstanceType() {
        return instanceType;
    }
}
