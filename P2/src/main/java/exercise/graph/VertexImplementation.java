package main.java.exercise.graph;

import main.java.exercise.helper.Point;
import main.java.framework.graph.Vertex;

public class VertexImplementation extends Vertex {

    private Point position;

    public VertexImplementation(int id, Point position) {
        super(id);
        this.position = position;
    }

    public Point getPosition() {
        return this.position;
    }
}
