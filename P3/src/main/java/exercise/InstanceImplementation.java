package main.java.exercise;

import main.java.framework.Instance;

public class InstanceImplementation implements Instance {

    private String groupName;
    private int number;
    private int[] keys;
    private int numberOfKeys;
    private String keyGroup;
    private boolean testOnlySimple;
    private boolean testOnlyAVL;

    public InstanceImplementation(String groupName, int number, int[] keys, int numberOfKeys, String keyGroup, boolean testOnlySimple, boolean testOnlyAVL) {
        this.groupName = groupName;
        this.number = number;
        this.keys = keys;
        this.numberOfKeys = numberOfKeys;
        this.keyGroup = keyGroup;
        this.testOnlySimple = testOnlySimple;
        this.testOnlyAVL = testOnlyAVL;
    }

    @Override
    public String getGroupName() {
        return this.groupName;
    }

    @Override
    public int getNumber() {
        return this.number;
    }

    public int[] getKeys() {
        return keys;
    }

    public int getNumberOfKeys() {
        return numberOfKeys;
    }

    public String getKeyGroup() {
        return keyGroup;
    }

    public boolean isTestOnlySimple() {
        return testOnlySimple;
    }

    public boolean isTestOnlyAVL() {
        return testOnlyAVL;
    }
}
