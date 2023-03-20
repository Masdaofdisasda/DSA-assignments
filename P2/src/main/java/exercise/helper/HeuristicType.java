package main.java.exercise.helper;

public enum HeuristicType {
    EUCLIDEAN_DISTANCE,
    MANHATTAN_DISTANCE,
    SHORTEST_PATH,
    ZERO;

    public static HeuristicType getHeuristicTypeByName(String name) {
        if (name.equals("EUCLIDEAN_DISTANCE")) {
            return HeuristicType.EUCLIDEAN_DISTANCE;
        } else if (name.equals("MANHATTAN_DISTANCE")) {
            return HeuristicType.MANHATTAN_DISTANCE;
        } else if (name.equals("SHORTEST_PATH")) {
            return HeuristicType.SHORTEST_PATH;
        } else if (name.equals("ZERO")) {
            return HeuristicType.ZERO;
        }
        return null;
    }
}
