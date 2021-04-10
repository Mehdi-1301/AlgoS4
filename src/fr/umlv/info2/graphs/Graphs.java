package fr.umlv.info2.graphs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Graphs {

    public static List<Integer> DFS(Graph g, int v0)
    {
        boolean[] visited = new boolean[g.numberOfVertices()];
        ArrayList<Integer> list = new ArrayList<>();

        for(int i = v0; i < visited.length; i++)
            if(!visited[i])
                recDFS(g,i,visited,list);

        return list;
    }

    private static void recDFS(Graph g, int i, boolean[] visited, List<Integer> list)
    {
        visited[i] = true;
        list.add(i);
        g.forEachEdge(i, edge -> {
            var vertice = edge.getEnd();
            if(!visited[vertice])
                recDFS(g,vertice,visited,list);
        });
    }

    public static List<Integer> BFS(Graph g, int v0)
    {
        boolean[] visited = new boolean[g.numberOfVertices()];
        ArrayList<Integer> list = new ArrayList<>();
        ArrayDeque<Integer> queue = new ArrayDeque<>();

        queue.add(v0);
        recBFS(g,visited,list,queue);

        for(int i = 0; i < visited.length; i++)
            if(!visited[i])
            {
                queue.add(i);
                recBFS(g,visited,list,queue);
            }


        return list;
    }

    private static void recBFS(Graph g,boolean[] visited, List<Integer> list, Deque<Integer> queue)
    {
        if(queue.isEmpty())
            return;
        var parent = queue.poll();
        list.add(parent);
        visited[parent] = true;

        g.forEachEdge(parent, edge -> {
            var child = edge.getEnd();
            if(visited[child])
                return;
            if(!visited[child])
                queue.add(child);
        });
        recBFS(g,visited,list,queue);
    }

    public static Graph randomGraph(int nVertices) {
        Graph graph = new AdjGraph(nVertices);
        Random random = new Random();
        for (int i = 0; i < nVertices * 2; i++) {
            var start = random.nextInt(nVertices);
            var end = random.nextInt(nVertices);
            var value = random.nextInt(nVertices) + 1;
            if (!graph.isEdge(start, end))
                graph.addEdge(start, end, value);
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

    public static void writeInFile(Path path, String string) throws IOException {
        Files.writeString(path, string, StandardCharsets.UTF_8);
    }
}
