package main.java.exercise;

import main.java.exercise.graph.EdgeImplementation;
import main.java.exercise.graph.Graph;
import main.java.exercise.helper.HeuristicType;
import main.java.exercise.helper.InstanceType;
import main.java.exercise.helper.Point;
import main.java.exercise.graph.VertexImplementation;
import main.java.exercise.instace_implementation.AStarInstanceImplementation;
import main.java.exercise.instace_implementation.CommonInstanceImplementation;
import main.java.exercise.instace_implementation.HeuristicsInstanceImplementation;
import main.java.exercise.result_implementation.CommonResultImplementation;
import main.java.framework.InstanceSet;
import main.java.framework.graph.GraphUtil;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.io.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;


public class InstanceSetImplementation extends InstanceSet<CommonInstanceImplementation, StudentSolutionImplementation, CommonResultImplementation, VerifierImplementation, SimpleDirectedWeightedGraph> {

    public InstanceSetImplementation(Path instanceSetPath, Path outputPath) {
        super(instanceSetPath, outputPath, CommonResultImplementation.class);
    }

    private double[][] shortestPathsGrid = null;
    private double[][] shortestPathsStreet = null;

    @Override
    protected CommonInstanceImplementation instanceFromCsv(String line) {
        String[] splitLine = line.split(",");
        int number = Integer.parseInt(splitLine[0]);
        String groupName = splitLine[1];
        switch (InstanceType.getInstanceTypeByGroupName(groupName)) {
            case EUCLIDEAN_DISTANCE_TEST:
            case MANHATTAN_DISTANCE_TEST:
                int point1X = Integer.parseInt(splitLine[2]);
                int point1Y = Integer.parseInt(splitLine[3]);
                Point point1 = new Point(point1X, point1Y);
                int point2X = Integer.parseInt(splitLine[4]);
                int point2Y = Integer.parseInt(splitLine[5]);
                Point point2 = new Point(point2X, point2Y);
                double expectedHeuristicsResult = Double.parseDouble(splitLine[6]);
                return new HeuristicsInstanceImplementation(groupName, number, point1, point2, expectedHeuristicsResult);
            case GRID_GRAPH:
            case STREET_GRAPH:
                String graphFileName = splitLine[2];
                HeuristicType heuristicType = HeuristicType.getHeuristicTypeByName(splitLine[3]);
                int source = Integer.parseInt(splitLine[4]);
                int target = Integer.parseInt(splitLine[5]);
                double expectedAStarResult = Double.parseDouble(splitLine[6]);
                SimpleDirectedWeightedGraph additionalInputTest = this.getAdditionalInput(graphFileName);
                Graph graph = new Graph(additionalInputTest);
                switch (InstanceType.getInstanceTypeByGroupName(groupName)) {
                    case GRID_GRAPH:
                        if (this.shortestPathsGrid == null) {
                            this.shortestPathsGrid = this.fetchShortestDistances("shortest-paths-grid.csv");
                        }
                        return new AStarInstanceImplementation(groupName, number, graph, heuristicType, source, target, expectedAStarResult, this.shortestPathsGrid);
                    case STREET_GRAPH:
                        if (this.shortestPathsStreet == null) {
                            this.shortestPathsStreet = this.fetchShortestDistances("shortest-paths-street.csv");
                        }
                        return new AStarInstanceImplementation(groupName, number, graph, heuristicType, source, target, expectedAStarResult, this.shortestPathsStreet);
                }
        }

        return null;
    }

    @Override
    protected StudentSolutionImplementation provideStudentSolution() {
        return new StudentSolutionImplementation();
    }

    @Override
    protected VerifierImplementation provideVerifier() {
        return new VerifierImplementation();
    }

    @Override
    protected SimpleDirectedWeightedGraph<VertexImplementation, EdgeImplementation> parseAdditionalInput(BufferedReader reader) {
        SimpleDirectedWeightedGraph<VertexImplementation, EdgeImplementation> graph = new SimpleDirectedWeightedGraph<VertexImplementation, EdgeImplementation>(EdgeImplementation.class);;

        VertexProvider<VertexImplementation> vertexProvider = (String id, Map<String, Attribute> attributes) -> {
            int x = Integer.parseInt(attributes.get("x").getValue());
            int y = Integer.parseInt(attributes.get("y").getValue());
            return new VertexImplementation(Integer.parseInt(id), new Point(x, y));
        };
        EdgeProvider<VertexImplementation, EdgeImplementation> edgeProvider = (VertexImplementation from, VertexImplementation to, String label, Map<String, Attribute> attributes) -> new EdgeImplementation(from, to);

        try {
            GraphMLImporter<VertexImplementation, EdgeImplementation> importer = GraphUtil.createImporter(vertexProvider, edgeProvider, "length");
            importer.importGraph(graph, reader);
            return graph;
        } catch(ImportException e) {
            e.printStackTrace();
            return null;
        }
    }

    private double[][] fetchShortestDistances(String fileName) {
        Path inputPath = FileSystems.getDefault().getPath("additional-input", fileName);
        BufferedReader inputReader;
        try {
            inputReader = Files.newBufferedReader(inputPath);
            String line;
            double[][] shortestPaths = new double[26][26];
            int count = 1;
            while((line = inputReader.readLine()) != null && count <= 25) {
                String[] splitLine = line.split(",");
                for (int i = 0; i < splitLine.length; i++) {
                    shortestPaths[i + 1][count] = Double.parseDouble(splitLine[i]);
                }
                count++;
            }
            return shortestPaths;
        } catch (IOException e) {
            System.err.println("Error while reading shortest paths");
            return null;
        }
    }

}
