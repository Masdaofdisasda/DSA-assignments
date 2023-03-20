package main.java.exercise;

import main.java.exercise.helper.Heuristic;
import main.java.exercise.helper.InstanceType;
import main.java.exercise.helper.PriorityQueue;
import main.java.exercise.helper.PriorityQueueLogs;
import main.java.exercise.instace_implementation.AStarInstanceImplementation;
import main.java.exercise.instace_implementation.CommonInstanceImplementation;
import main.java.exercise.instace_implementation.HeuristicsInstanceImplementation;
import main.java.exercise.result_implementation.AStarResultImplementation;
import main.java.exercise.result_implementation.CommonResultImplementation;
import main.java.exercise.result_implementation.HeuristicsResultImplementation;
import main.java.framework.*;

public class VerifierImplementation extends Verifier<CommonInstanceImplementation, StudentSolutionImplementation, CommonResultImplementation> {

    @Override
    public CommonResultImplementation solveProblemUsingStudentSolution(CommonInstanceImplementation instance, StudentSolutionImplementation studentSolution) {
        switch (instance.getInstanceType()) {
            case EUCLIDEAN_DISTANCE_TEST:
                HeuristicsInstanceImplementation euclideanInstanceImplementation = (HeuristicsInstanceImplementation) instance;
                double euclideanResult = studentSolution.heuristicEuclideanDistance(
                        euclideanInstanceImplementation.getPoint1(),
                        euclideanInstanceImplementation.getPoint2()
                );
                return new HeuristicsResultImplementation(InstanceType.EUCLIDEAN_DISTANCE_TEST, euclideanResult);
            case MANHATTAN_DISTANCE_TEST:
                HeuristicsInstanceImplementation manhattanInstanceImplementation = (HeuristicsInstanceImplementation) instance;
                double manhattanResult = studentSolution.heuristicManhattanDistance(
                        manhattanInstanceImplementation.getPoint1(),
                        manhattanInstanceImplementation.getPoint2()
                );
                return new HeuristicsResultImplementation(InstanceType.MANHATTAN_DISTANCE_TEST, manhattanResult);
            case GRID_GRAPH:
            case STREET_GRAPH:
                AStarInstanceImplementation aStarInstanceImplementation = (AStarInstanceImplementation) instance;
                PriorityQueueLogs logs = new PriorityQueueLogs();
                PriorityQueue q = new PriorityQueue(logs);
                Heuristic h = new Heuristic(
                        studentSolution,
                        aStarInstanceImplementation.getHeuristicType(),
                        aStarInstanceImplementation.getGraph(),
                        aStarInstanceImplementation.getGraph().getVertexPosition(
                                aStarInstanceImplementation.getTarget()
                        ),
                        aStarInstanceImplementation.getShortestPaths()[aStarInstanceImplementation.getTarget()]
                );
                int[] path = new int[aStarInstanceImplementation.getGraph().numberOfVertices()];
                studentSolution.aStar(
                        aStarInstanceImplementation.getGraph(),
                        q,
                        h,
                        aStarInstanceImplementation.getSource(),
                        aStarInstanceImplementation.getTarget(),
                        path
                );
                return new AStarResultImplementation(
                        aStarInstanceImplementation.getInstanceType(),
                        path,
                        aStarInstanceImplementation.getHeuristicType(),
                        aStarInstanceImplementation.getSource(),
                        aStarInstanceImplementation.getTarget(),
                        logs.getLogs()
                );
        }
        return null;
    }

    @Override
    public Report verifyResult(CommonInstanceImplementation instance, CommonResultImplementation result) {
        switch (instance.getInstanceType()) {
            case EUCLIDEAN_DISTANCE_TEST:
            case MANHATTAN_DISTANCE_TEST:
                HeuristicsInstanceImplementation heuristicsInstanceImplementation = (HeuristicsInstanceImplementation) instance;
                HeuristicsResultImplementation heuristicsResultImplementation = (HeuristicsResultImplementation) result;
                if (heuristicsInstanceImplementation.getExpectedResult()
                        == heuristicsResultImplementation.getResult()) {
                    return new Report(true, "");
                } else {
                    return new Report(false, "Error in instance " + instance.getNumber() + " " + this.gatherHeuristicsInstanceInformation(heuristicsInstanceImplementation) + ": Heuristic value was expected to be " + heuristicsInstanceImplementation.getExpectedResult() +  " but was " + heuristicsResultImplementation.getResult() + ".");
                }
            case GRID_GRAPH:
            case STREET_GRAPH:
                AStarInstanceImplementation aStarInstanceImplementation = (AStarInstanceImplementation) instance;
                AStarResultImplementation aStarResultImplementation = (AStarResultImplementation) result;
                double distance = 0;
                int[] path = aStarResultImplementation.getPath();
                if (path[path.length - 1] == 0) {
                    return new Report(false, "Error in instance " + instance.getNumber() + " " + this.gatherAStarInstanceInformation(aStarInstanceImplementation) + ": No path was provided.");
                }
                if (path[path.length - 1] != aStarInstanceImplementation.getTarget()) {
                    return new Report(false, "Error in instance " + instance.getNumber() + " " + this.gatherAStarInstanceInformation(aStarInstanceImplementation) + ": Target was expected to be " + aStarInstanceImplementation.getTarget() + " but the target of the provided path was " + path[path.length - 1] + ".");
                }
                for (int i = path.length - 2; i >= 0; i--) {
                    if (path[i] <= 0) {
                        if (path[i + 1] != aStarInstanceImplementation.getSource()) {
                            return new Report(false, "Error in instance " + instance.getNumber() + " " + this.gatherAStarInstanceInformation(aStarInstanceImplementation) + ": Source was expected to be " + aStarInstanceImplementation.getSource() + " but the source of the provided path was " + path[i + 1] + ".");
                        }
                        break;
                    }
                    double weight = aStarInstanceImplementation.getGraph().getEdgeWeight(path[i], path[i+1]);
                    if (weight >= 0) {
                        distance += weight;
                    } else {
                        return new Report(false, "Error in instance " + instance.getNumber() + " " + this.gatherAStarInstanceInformation(aStarInstanceImplementation) + ": Provided path contains a non-existent edge (" + path[i] + "," + path[i+1] + ").");
                    }
                }
                if (distance == aStarInstanceImplementation.getExpectedResult()) {
                    return new Report(true, "");
                } else {
                    return new Report(false, "Error in instance " + instance.getNumber() + " " + this.gatherAStarInstanceInformation(aStarInstanceImplementation) + ": Distance was expected to be " + aStarInstanceImplementation.getExpectedResult() +  " but was " + distance + ".");
                }
        }
        return null;
    }

    private String gatherHeuristicsInstanceInformation(HeuristicsInstanceImplementation heuristicsInstanceImplementation) {
        return "(" + heuristicsInstanceImplementation.getGroupName() + ")";
    }

    private String gatherAStarInstanceInformation(AStarInstanceImplementation aStarInstanceImplementation) {
        return "(" + aStarInstanceImplementation.getGroupName() + ", " + aStarInstanceImplementation.getHeuristicType() + ")";
    }
}
