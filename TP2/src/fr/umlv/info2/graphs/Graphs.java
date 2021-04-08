package fr.umlv.info2.graphs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Graphs {

    public static void writeInFile(Path path, String string) throws IOException {
        Files.writeString(path, string, StandardCharsets.UTF_8);
    }

    public static List<Integer> DFS(Graph g, int v0) {
        List<Integer> list = new ArrayList<>();
        boolean[] visited = new boolean[g.numberOfVertices()];
        recDFS(g, v0, list, visited);
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                recDFS(g, i, list, visited);
            }
        }
        return list;
    } // depth - first

    public static List<Integer> BFS(Graph g, int v0) {
        List<Integer> list = new ArrayList<>();
        boolean[] visited = new boolean[g.numberOfVertices()];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(v0);
        recBFS(g, queue, list, visited);
        for (boolean b : visited) {
            if (!b) {
                recBFS(g, queue, list, visited);
            }
        }
        return list;
    } // breadth - first

    private static void recBFS(Graph g, Queue<Integer> queue, List<Integer> list, boolean[] visited) {
        if (queue.isEmpty()) {
            return;
        }
        int current = queue.poll();
        list.add(current);
        g.forEachEdge(current, edge -> {
            if (!visited[edge.getEnd()]) {
                visited[edge.getEnd()] = true;
                queue.add(edge.getEnd());
            }
        });
        recBFS(g, queue, list, visited);
    }

    private static void recDFS(Graph g, int v0, List<Integer> list, boolean[] visited) {
        list.add(v0);
        visited[v0] = true;
        g.forEachEdge(v0, edge -> {
            if (!visited[edge.getEnd()]) {
                recDFS(g, edge.getEnd(), list, visited);
            }
        });
    }

    public static int[][] timedDepthFirstSearch(Graph g, int v0) {
        List<Integer> list = new ArrayList<>();
        boolean[] visited = new boolean[g.numberOfVertices()];
        int[][] results = new int[g.numberOfVertices()][2];
        LongAdder longAdder = new LongAdder();
        recTimedDFS(g, v0, list, visited, longAdder, results);
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                recTimedDFS(g, i, list, visited, longAdder, results);
            }
        }
        return results;
    }

    private static void recTimedDFS(Graph g, int v0, List<Integer> list, boolean[] visited, LongAdder longAdder, int[][] results) {
        list.add(v0);
        visited[v0] = true;
        results[v0][0] = (int) longAdder.longValue();
        longAdder.increment();
        g.forEachEdge(v0, edge -> {
            if (!visited[edge.getEnd()]) {
                recTimedDFS(g, edge.getEnd(), list, visited, longAdder, results);
            }
        });
        results[v0][1] = (int) longAdder.longValue();
        longAdder.increment();
    }

    public static List<Integer> topologicalSort(Graph g, boolean cycleDetect) {
        Map<Integer, Integer> indexTable = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        boolean cycle;
        for (int i = 0; i < g.numberOfVertices(); i++) {
            indexTable.putIfAbsent(i, 0);
            g.forEachEdge(i, edge -> {
                indexTable.merge(edge.getEnd(), 1, Integer::sum);
            });
        }
        while (!indexTable.isEmpty()) {
            cycle = true;
            for (int i = 0; i < g.numberOfVertices(); i++) {
                Integer current = indexTable.get(i);
                if (current != null && current == 0) {
                    g.forEachEdge(i, edge -> {
                        indexTable.merge(edge.getEnd(), -1, Integer::sum);
                    });
                    list.add(i);
                    indexTable.remove(i);
                    cycle = false;
                }
            }
            if (cycle) {
                if (cycleDetect)
                    throw new IllegalStateException("Cycle detected");
                break;
            }
        }
        return list;
    }

    public static List<List<Integer>> scc(Graph g) {
        int[][] timedDFS = timedDepthFirstSearch(g, 0);
        List<Map.Entry<Integer, Integer>> ordered = new ArrayList<>();
        for (int i = 0; i < timedDFS.length; i++) {
            ordered.add(Map.entry(i, timedDFS[i][1]));
        }
        ordered.sort(Comparator.comparingInt(Map.Entry::getValue));
        Collections.reverse(ordered);
        Graph transpose = g.transpose();
        List<List<Integer>> results = new ArrayList<>();
        boolean[] visited = new boolean[g.numberOfVertices()];
        ordered.forEach(entry -> {
            if (!visited[entry.getKey()]) {
                List<Integer> treeNodes = new ArrayList<>();
                recDFS(transpose, entry.getKey(), treeNodes, visited);
                results.add(treeNodes);
            }
        });
        return results;
    }

    public static Graph randomGraph(int nbVertex) {
        Graph graph = new MatGraph(nbVertex);
        Random random = new Random();
        for (int i = 0; i < nbVertex * 2; i++) {
            int ori = random.nextInt(nbVertex);
            int dst = random.nextInt(nbVertex);
            int value = random.nextInt(nbVertex) + 1;
            if (!graph.isEdge(ori, dst)) {
                graph.addEdge(ori, dst, value);
            } else {
                i--;
            }

        }
        return graph;
    }

    public static Graph readGraphFromFile(Path path) throws IOException, NumberFormatException {
        String string = Files.readString(path);
        String[] lines = string.split("\r\n");
        int vertexSize = Integer.parseInt(lines[0]);
        Graph graph = new MatGraph(vertexSize);
        lines = Arrays.stream(lines).skip(1).collect(Collectors.toList()).toArray(String[]::new);
        for (int i = 0; i < vertexSize; i++) {
            String[] digit = lines[i].split(" ");
            for (int j = 0; j < vertexSize; j++) {
                int value = Integer.parseInt(digit[j]);
                if (value != 0) {
                    graph.addEdge(i, j, value);
                }
            }
        }
        return graph;
    }
}
