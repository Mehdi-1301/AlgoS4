package fr.umlv.info2.graphs;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public class MatGraph implements Graph{

    private final int[][] mat;
    private final int n;
    private int e = 0;

    public MatGraph(int nVertices) {
        if(nVertices <= 0)
            throw new IllegalArgumentException("The number of Vertices must be superior to 1");
        this.n = nVertices;
        this.mat = new int[nVertices][nVertices];
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
        Objects.checkIndex(i,n);
        Objects.checkIndex(j,n);
        if(value == 0)
            throw new IllegalArgumentException("The value can't equal 0");
        e++;
        mat[i][j] = value;
    }

    @Override
    public boolean isEdge(int i, int j) {
        Objects.checkIndex(i,n);
        Objects.checkIndex(j,n);
        return mat[i][j] != 0;
    }

    @Override
    public int getWeight(int i, int j) {
        Objects.checkIndex(i,n);
        Objects.checkIndex(j,n);
        return mat[i][j];
    }

    @Override
    public Iterator<Edge> edgeIterator(int i) {
        Objects.checkIndex(i,n);

        return new Iterator<>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                for (int j = current; j < n; j++) {
                    if (isEdge(i, j)) {
                        current = j;
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Edge next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                current++;
                return new Edge(i, current - 1, mat[i][current - 1]);
            }
        };
    }

    @Override
    public void forEachEdge(int i, Consumer<Edge> consumer) {
        Objects.checkIndex(i,n);
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
