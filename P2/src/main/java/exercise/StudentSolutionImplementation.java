package main.java.exercise;

import main.java.exercise.graph.Graph;
import main.java.exercise.helper.Heuristic;
import main.java.exercise.helper.Point;
import main.java.exercise.helper.PriorityQueue;
import main.java.framework.StudentInformation;
import main.java.framework.StudentSolution;

public class StudentSolutionImplementation implements StudentSolution {
    @Override
    public StudentInformation provideStudentInformation() {
        return new StudentInformation(
                "David", // Vorname
                "Köppl", // Nachname
                "12022493" // Matrikelnummer
        );
    }

    // Implementieren Sie hier Ihre Lösung für die manhattan Distanz
    public double heuristicManhattanDistance(Point point1, Point point2) {
        return Math.abs(point1.getX() - point2.getX()) + Math.abs(point1.getY() - point2.getY());
    }

    // Implementieren Sie hier Ihre Lösung für die euklidische Distanz
    public double heuristicEuclideanDistance(Point point1, Point point2) {
        return Math.sqrt(Math.pow(point2.getX() - point1.getX(), 2) + Math.pow(point2.getY() - point1.getY(), 2));
    }

    // Implementieren Sie hier Ihre Lösung für A*
    public void aStar(Graph g, PriorityQueue q, Heuristic h, int source, int target, int[] path) {
        if (source == target) {
            path[path.length - 1] = target;
            return;
        }

        double[] gScore = new double[g.numberOfVertices() + 1];
        for (int i = 1; i <= g.numberOfVertices(); i++) {   //g(x) = inf
            gScore[i] = Double.MAX_VALUE;
        }
        gScore[source] = 0;  //g(s) = 0

        int[] pred = new int[g.numberOfVertices() + 1]; //pred(x) = 0

        q.add(source, h.evaluate(source));  //Q = (s,h(s))

        while (!q.isEmpty()) {  //while Q != 0)
            int x = q.removeFirst();    //x = Knoten min. Kosten

            if (x == target) {  //x=t
                gScore[target] = gScore[target] + h.evaluate(target);
                sortPred(pred, path, source, target);
                break;
            }

            int[] v = g.getNeighbors(x);

            for (int i = 0; i < v.length; i++) {    //for all (x,v)
                double gCandidate = gScore[x] + g.getEdgeWeight(x, v[i]);

                if (gCandidate < gScore[v[i]]) {
                    gScore[v[i]] = gCandidate;

                    pred[v[i]] = x;

                    if (q.contains(v[i])) {
                        q.decreaseWeight(v[i], gCandidate + h.evaluate(v[i]));
                    } else {
                        q.add(v[i], gCandidate + h.evaluate(v[i]));
                    }
                }
            }
        }

    }

    private void sortPred(int[] pred, int[] path, int source, int target) {
        int x = target;
        int i = pred.length - 2;
        while (x != source) {
            path[i] = x;
            i--;
            x = pred[x];
        }
        path[i] = source;
    }

}
