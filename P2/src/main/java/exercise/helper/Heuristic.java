package main.java.exercise.helper;

import main.java.exercise.StudentSolutionImplementation;
import main.java.exercise.graph.Graph;

public class Heuristic {

    private StudentSolutionImplementation studentSolutionImplementation;

    private HeuristicType type;

    private Graph g;

    private Point targetPosition;

    private double[] shortestPathsToTarget;

    public Heuristic(StudentSolutionImplementation studentSolutionImplementation,
                     HeuristicType type,
                     Graph g,
                     Point targetPosition,
                     double[] shortestPathsToTarget) {
        this.studentSolutionImplementation = studentSolutionImplementation;
        this.type = type;
        this.g = g;
        this.targetPosition = targetPosition;
        this.shortestPathsToTarget = shortestPathsToTarget;
    }

    public double evaluate(int vertex) {
        switch(this.type) {
            case EUCLIDEAN_DISTANCE:
                return this.studentSolutionImplementation.heuristicEuclideanDistance(g.getVertexPosition(vertex), this.targetPosition);
            case MANHATTAN_DISTANCE:
                return this.studentSolutionImplementation.heuristicManhattanDistance(g.getVertexPosition(vertex), this.targetPosition);
            case SHORTEST_PATH:
                return this.shortestPathsToTarget[vertex];
            case ZERO:
                return 0;
        }
        return 0;
    }
}
