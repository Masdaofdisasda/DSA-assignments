package main.java.exercise.helper;

public class PriorityQueueLogs {

    private String logs = "";

    public void appendExpandedVertex(int vertexId) {
        if (this.logs.length() > 0) {
            logs += "|";
        }
        logs += vertexId;
    }

    public void appendPredecessorSet(int vertexId) {
        if (this.logs.length() > 0) {
            logs += "<" + vertexId;
        }
    }

    public String getLogs() {
        return logs;
    }
}
