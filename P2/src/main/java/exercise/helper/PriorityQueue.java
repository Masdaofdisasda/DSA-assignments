package main.java.exercise.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriorityQueue {

    private PriorityQueueLogs logs;
    private Map<Integer, Integer> vertexIdToHeapIndex = new HashMap<Integer, Integer>();
    private List<WeightedValue<Integer>> heap = new ArrayList<WeightedValue<Integer>>();

    public PriorityQueue(PriorityQueueLogs logs) {
        this.heap.add(null);
        this.logs = logs;
    }

    public void add(int vertexId, double weight) {
        Integer index = this.vertexIdToHeapIndex.get(vertexId);
        if (index != null) {
            return;
        }
        this.vertexIdToHeapIndex.put(vertexId, this.heap.size());
        this.heap.add(new WeightedValue<Integer>(weight, vertexId));
        this.heapifyUp(this.heap.size() - 1);
        this.logs.appendPredecessorSet(vertexId);
    }

    public boolean isEmpty() {
        return this.heap.size() <= 1;
    }

    public int length() {
        return this.heap.size();
    }

    public boolean contains(int vertexId) {
        return this.vertexIdToHeapIndex.get(vertexId) != null;
    }

    public int removeFirst() {
        int size = this.heap.size();
        if (size > 1) {
            int minVertexId = this.heap.get(1).value;
            this.swap(1, size - 1);
            this.vertexIdToHeapIndex.remove(minVertexId);
            this.heap.remove(size - 1);
            this.heapifyDown(1);
            this.logs.appendExpandedVertex(minVertexId);
            return minVertexId;
        } else {
            return -1;
        }
    }

    public void decreaseWeight(int vertexId, double weight) {
        Integer index = this.vertexIdToHeapIndex.get(vertexId);
        if (index == null) {
            return;
        }
        WeightedValue<Integer> entry = this.heap.get(index);
        if (entry.getWeight() > weight) {
            entry.setWeight(weight);
            this.heapifyUp(index);
            this.logs.appendPredecessorSet(vertexId);
        }
    }

    private void swap(int index1, int index2) {
        if (0 < index1 && index1 < this.heap.size() && 0 < index2 && index2 < this.heap.size()) {
            WeightedValue<Integer> content1 = this.heap.get(index1);
            WeightedValue<Integer> content2 = this.heap.get(index2);
            this.vertexIdToHeapIndex.put(content1.value, index2);
            this.vertexIdToHeapIndex.put(content2.value, index1);
            this.heap.set(index1, content2);
            this.heap.set(index2, content1);
        }
    }

    private double getWeight(int index) {
        if (0 < index && index < this.heap.size()) {
            return this.heap.get(index).getWeight();
        } else {
            return -1;
        }
    }

    private void heapifyUp(int index) {
        if (index > 1) {
            int j = (int) Math.floor(index / 2);
            if (this.getWeight(index) < this.getWeight(j)) {
                this.swap(index, j);
                this.heapifyUp(j);
            }
        }
    }

    private void heapifyDown(int index) {
        int n = this.length() - 1;
        int j;
        if (2 * index > n) {
            return;
        } else if (2 * index < n) {
            int left = 2 * index;
            int right = 2 * index + 1;
            if (this.getWeight(left) < this.getWeight(right)) {
                j = left;
            } else {
                j = right;
            }
        } else {
            j = 2 * index;
        }
        if (this.getWeight(j) < this.getWeight(index)) {
            this.swap(index, j);
            this.heapifyDown(j);
        }
    }

    private static class WeightedValue<ValueT> {
        private Double weight;
        private ValueT value;

        public WeightedValue(Double weight, ValueT value) {
            this.weight = weight;
            this.value = value;
        }

        public Double getWeight() {
            return weight;
        }

        public void setWeight(Double weight) {
            this.weight = weight;
        }

        public ValueT getValue() {
            return value;
        }

        public void setValue(ValueT value) {
            this.value = value;
        }
    }

}
