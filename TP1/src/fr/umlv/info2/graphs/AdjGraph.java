package fr.umlv.info2.graphs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class AdjGraph implements Graph {
    private final ArrayList<LinkedList<Edge>> adj;
    private final int n;

    public AdjGraph(int numberOfVertices) {
        if (numberOfVertices < 0) {
            throw new IllegalArgumentException();
        }
        this.n = numberOfVertices;
        this.adj = new ArrayList<>(numberOfVertices);
        for (int i = 0; i < numberOfVertices; i++) {
            this.adj.add(new LinkedList<>());
        }
    }

    @Override
    public int numberOfEdges() {
        return Long.valueOf(adj
                .stream()
                .mapToLong(LinkedList::size)
                .sum()).intValue();
    }

    @Override
    public int numberOfVertices() {
        return n;
    }

    @Override
    public void addEdge(int i, int j, int value) {
        if (i >= n || j >= n || value == 0) {
            throw new IllegalArgumentException();
        }
        this.adj.get(i).add(new Edge(i, j, value));
    }

    @Override
    public boolean isEdge(int i, int j) {
        if (i >= n || j >= n) {
            throw new IllegalArgumentException();
        }
        return this.adj.get(i).stream().anyMatch(edge -> edge.getStart() == i && edge.getEnd() == j);
    }

    @Override
    public int getWeight(int i, int j) {
        if (i >= n || j >= n) {
            throw new IllegalArgumentException();
        }
        return this.adj.get(i)
                .stream()
                .filter(edge -> edge.getStart() == i && edge.getEnd() == j)
                .map(Edge::getValue)
                .findFirst()
                .orElse(0);
    }

    @Override
    public Iterator<Edge> edgeIterator(int i) {
        if (i >= n) {
            throw new IllegalArgumentException();
        }
        return this.adj.get(i).iterator();
    }
}
