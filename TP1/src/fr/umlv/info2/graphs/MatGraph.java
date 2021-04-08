package fr.umlv.info2.graphs;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class MatGraph implements Graph {
    private final int[][] mat;
    private final int n;

    public MatGraph(int numberOfVertices) {
        if (numberOfVertices < 0) {
            throw new IllegalArgumentException();
        }
        this.n = numberOfVertices;
        this.mat = new int[numberOfVertices][numberOfVertices];
    }

    @Override
    public int numberOfEdges() {
        return Arrays.stream(mat)
                .mapToInt(row -> Arrays.stream(row).reduce(0 , (a, b) -> {
                    if (a != 0 && b != 0) {
                        return 2;
                    } else if (a != 0 || b != 0) {
                        return 1;
                    }
                    return 0;
                }))
                .reduce(0, Integer::sum);
    }

    @Override
    public int numberOfVertices() {
        return this.n;
    }

    @Override
    public void addEdge(int i, int j, int value) {
        if (i >= n || j >= n || value == 0) {
            throw new IllegalArgumentException();
        }
        mat[i][j] = value;
    }

    @Override
    public boolean isEdge(int i, int j) {
        if (i >= n || j >= n) {
            throw new IllegalArgumentException();
        }
        return mat[i][j] != 0;
    }

    @Override
    public int getWeight(int i, int j) {
        if (i >= n || j >= n) {
            throw new IllegalArgumentException();
        }
        return mat[i][j];
    }

    private static Optional<Edge> getNextEdge(int current, int[] row, int start) {
        if (current < row.length) {
            for (int i = current; i < row.length; i++) {
                if (row[i] != 0) {
                    return Optional.of(new Edge(start, i, row[i]));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Iterator<Edge> edgeIterator(int i) {
        if (i >= n) {
            throw new IllegalArgumentException();
        }
        return new Iterator<>() {

            private Optional<Edge> current = getNextEdge(0, mat[i], i);
            @Override
            public boolean hasNext() {
                return current.isPresent();
            }

            @Override
            public Edge next() {
                if (!hasNext()) {
                    throw new UnsupportedOperationException();
                }
                var toReturn = current;
                current = getNextEdge(current.get().getEnd() + 1, mat[i], i);
                return toReturn.orElseThrow();
            }
        };
    }

    @Override
    public String toGraphviz() {
        var stringBuilder = new StringBuilder("digraph G {\n");
        IntStream.range(0, n).forEach(
            i -> {
                stringBuilder.append('\t');
                stringBuilder.append(i);
                stringBuilder.append(";\n");
                IntStream.range(0, n).forEach(
                    j -> {
                        if (mat[i][j] != 0) {
                            stringBuilder.append('\t');
                            stringBuilder.append(i);
                            stringBuilder.append(" -> ");
                            stringBuilder.append(j);
                            stringBuilder.append(" [label=\"");
                            stringBuilder.append(mat[i][j]);
                            stringBuilder.append("\" ] ;\n");
                        }
                    }
                );
            }
        );
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
