package main.java.exercise.graph;

import main.java.exercise.helper.Point;
import main.java.framework.graph.BasicGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class Graph extends BasicGraph<VertexImplementation, EdgeImplementation> {

    public Graph(SimpleDirectedWeightedGraph<VertexImplementation, EdgeImplementation> graph) {
        super(graph);
    }

    public double getEdgeWeight(int vertexIdStart, int vertexIdEnd) {
        EdgeImplementation edgeImpl = super.edges.get(new Tuple<Integer, Integer>(vertexIdStart, vertexIdEnd));
        if (edgeImpl == null) {
            return -1;
        }

        return this.graph.getEdgeWeight(edgeImpl);
    }

    public Point getVertexPosition(int vertexId) {
        VertexImplementation vertexImpl = super.vertices.get(vertexId);
        return vertexImpl.getPosition();
    }


}
