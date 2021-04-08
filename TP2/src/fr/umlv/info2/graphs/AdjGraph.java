package fr.umlv.info2.graphs;

import fr.umlv.info2.graphs.Edge;
import fr.umlv.info2.graphs.Graph;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class AdjGraph implements Graph {
    private final ArrayList<LinkedList<Edge>> adj;
    private final int n; // number of vertices
    private int e;

    public AdjGraph(int n) {
        this.n = n;
        adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adj.add(new LinkedList<>());
        }
    }

    @Override
    public int numberOfEdges() {
        return e;
    }

    @Override
    public int numberOfVertices() {
        return n;
    }

    @Override
    public void addEdge(int i, int j, int value) {
        Objects.checkIndex(i, n);
        Objects.checkIndex(j, n);
        if (value <= 0) {
            throw new IllegalArgumentException();
        }
        adj.get(i).add(new Edge(i, j, value));
        e++;
    }

    @Override
    public boolean isEdge(int i, int j) {
        Objects.checkIndex(i, n);
        Objects.checkIndex(j, n);
        return adj.get(i).stream().anyMatch(edge -> edge.getStart() == i && edge.getEnd() == j);
    }

    @Override
    public int getWeight(int i, int j) {
        Objects.checkIndex(i, n);
        Objects.checkIndex(j, n);
        Optional<Edge> first = adj.get(i).stream().filter(edge -> edge.getStart() == i && edge.getEnd() == j).findFirst();
        if (first.isPresent()) {
            return first.get().getValue();
        }
        throw new IllegalStateException();
    }

    @Override
    public Iterator<Edge> edgeIterator(int i) {
        Objects.checkIndex(i, n);
        return new Iterator<Edge>() {
            int tmp;

            @Override
            public boolean hasNext() {
                return tmp < adj.get(i).size();
            }

            @Override
            public Edge next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Edge edge = adj.get(i).get(tmp);
                tmp++;
                return edge;
            }
        };
    }

    @Override
    public void forEachEdge(int i, Consumer<Edge> consumer) {
        edgeIterator(i).forEachRemaining(consumer);
    }

    @Override
    public String toGraphviz() {
        StringBuilder stringBuilder = new StringBuilder("digraph G {\n");
        for (int i = 0; i < adj.size(); i++) {
            stringBuilder.append(i).append(";\n");
            Iterator<Edge> edgeIterator = edgeIterator(i);
            edgeIterator.forEachRemaining(edge -> stringBuilder.append(edge.getStart()).append(" -> ")
                    .append(edge.getEnd()).append(" [ label=\"").append(edge.getValue()).append("\" ] ;\n"));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
