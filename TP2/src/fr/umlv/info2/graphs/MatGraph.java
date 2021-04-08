package fr.umlv.info2.graphs;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public class MatGraph implements Graph {
    private final int[][] mat;
    private final int n; // number of vertices
    private int e = 0;// number of edges

    public MatGraph(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException();
        }
        n = size;
        mat = new int[n][n];
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
        mat[i][j] = value;
        e++;
    }

    @Override
    public boolean isEdge(int i, int j) {
        Objects.checkIndex(i, n);
        Objects.checkIndex(j, n);
        if (mat[i][j] != 0) {
            return true;
        }
        return false;
    }

    @Override
    public int getWeight(int i, int j) {
        Objects.checkIndex(i, n);
        Objects.checkIndex(j, n);
        return mat[i][j];
    }

    @Override
    public Iterator<Edge> edgeIterator(int i) {
        Objects.checkIndex(i, n);
        return new Iterator<Edge>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                for (int j = index; j < n; j++) {
                    if (isEdge(i, j)) {
                        index = j;
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Edge next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                index++;
                return new Edge(i, index - 1, mat[i][index - 1]);
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
        for (int i = 0; i < n; i++) {
            stringBuilder.append(i).append(";\n");
            Iterator<Edge> edgeIterator = edgeIterator(i);
            edgeIterator.forEachRemaining(edge -> stringBuilder.append(edge.getStart()).append(" -> ")
                    .append(edge.getEnd()).append(" [ label=\"").append(edge.getValue()).append("\" ] ;\n"));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
