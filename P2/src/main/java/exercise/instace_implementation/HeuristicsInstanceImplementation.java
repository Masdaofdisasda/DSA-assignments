package main.java.exercise.instace_implementation;

import main.java.exercise.helper.InstanceType;
import main.java.exercise.helper.Point;

public class HeuristicsInstanceImplementation extends CommonInstanceImplementation {

    private Point point1;
    private Point point2;
    private double expectedResult;

    public HeuristicsInstanceImplementation(String groupName, int number, Point point1, Point point2, double expectedResult) {
        super(
                groupName,
                number,
                groupName.equals("Euclidean Distance Test")
                        ? InstanceType.EUCLIDEAN_DISTANCE_TEST
                        : InstanceType.MANHATTAN_DISTANCE_TEST
        );
        this.point1 = point1;
        this.point2 = point2;
        this.expectedResult = expectedResult;
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public double getExpectedResult() {
        return expectedResult;
    }
}
