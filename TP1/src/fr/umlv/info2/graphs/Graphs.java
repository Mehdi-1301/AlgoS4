package fr.umlv.info2.graphs;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Graphs {

    private static void RecDFS(Graph g, int i, List<Boolean> visited, List<Integer> result) {
        visited.set(i, true);
        result.add(i);
        g.forEachEdge(i, edge -> {
            if (!visited.get(edge.getEnd())) {
                RecDFS(g, edge.getEnd(), visited, result);
            }
        });
    }

    public static List<Integer> DFS(Graph g, int v0) {
        var numberOfVertices = Objects.requireNonNull(g).numberOfVertices();
        if (v0 < 0 || v0 >= numberOfVertices) {
            throw new IllegalArgumentException();
        }
        var result = new ArrayList<Integer>(numberOfVertices);
        var visited = new ArrayList<Boolean>(numberOfVertices);
        IntStream.range(0, numberOfVertices).forEach(__ -> visited.add(false));
        for (int i = v0; i < numberOfVertices; i++) {
            if (!visited.get(i)) {
                RecDFS(g, i, visited, result);
            }
        }
        for (int i = 0; i < v0; i++) {
            if (!visited.get(i)) {
                RecDFS(g, i, visited, result);
            }
        }
        return result;
    }
    public static List<Integer> BFS(Graph g, int v0) {
        return null;
    }
}
