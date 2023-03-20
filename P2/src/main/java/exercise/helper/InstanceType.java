package main.java.exercise.helper;

public enum InstanceType {
    EUCLIDEAN_DISTANCE_TEST,
    MANHATTAN_DISTANCE_TEST,
    GRID_GRAPH,
    STREET_GRAPH;

    public static InstanceType getInstanceTypeByGroupName(String groupName) {
        if (groupName.equals("Euclidean Distance Test")) {
            return InstanceType.EUCLIDEAN_DISTANCE_TEST;
        } else if (groupName.equals("Manhattan Distance Test")) {
            return InstanceType.MANHATTAN_DISTANCE_TEST;
        } else if (groupName.equals("Grid")) {
            return InstanceType.GRID_GRAPH;
        } else if (groupName.equals("Street")) {
            return InstanceType.STREET_GRAPH;
        }
        return null;
    }
}
