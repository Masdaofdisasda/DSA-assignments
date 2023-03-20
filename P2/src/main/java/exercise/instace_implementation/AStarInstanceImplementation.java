package main.java.exercise.instace_implementation;

import main.java.exercise.graph.Graph;
import main.java.exercise.helper.HeuristicType;
import main.java.exercise.helper.InstanceType;

public class AStarInstanceImplementation extends CommonInstanceImplementation {

    private Graph graph;
    private HeuristicType heuristicType;
    private int source;
    private int target;
    private double expectedResult;
    private double[][] shortestPaths;

    public AStarInstanceImplementation(String groupName, int number, Graph graph, HeuristicType heuristicType, int source, int target, double expectedResult, double[][] shortestPaths) {
        super(
                groupName,
                number,
                groupName.equals("Grid")
                        ? InstanceType.GRID_GRAPH
                        : InstanceType.STREET_GRAPH
        );
        this.graph = graph;
        this.heuristicType = heuristicType;
        this.source = source;
        this.target = target;
        this.expectedResult = expectedResult;
        this.shortestPaths = shortestPaths;
    }

    public Graph getGraph() {
        return graph;
    }

    public HeuristicType getHeuristicType() {
        return heuristicType;
    }

    public int getSource() {
        return source;
    }

    public int getTarget() {
        return target;
    }

    public double getExpectedResult() {
        return expectedResult;
    }

    public double[][] getShortestPaths() {
        return shortestPaths;
    }
}
