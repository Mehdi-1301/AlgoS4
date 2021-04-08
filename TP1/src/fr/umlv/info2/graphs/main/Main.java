package fr.umlv.info2.graphs.main;

import fr.umlv.info2.graphs.AdjGraph;
import fr.umlv.info2.graphs.Graph;
import fr.umlv.info2.graphs.Graphs;
import fr.umlv.info2.graphs.MatGraph;

import java.nio.file.Path;
import java.util.stream.IntStream;

public class Main {

    private static void graphNFile() {
        var graphFromFile = Graph.makeGraphFromMatrixFile(Path.of("my_graph.mat"), MatGraph::new);
        System.out.println(graphFromFile.toGraphviz());
        Graph.saveGrapvizInFile(graphFromFile);
    }

    private static void testGraph(Graph graph) {
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 3);
        graph.addEdge(1, 3, 6);
        graph.addEdge(2, 0, 1);
        graph.addEdge(3, 1, 2);
        System.out.println("Number of edges : " + graph.numberOfEdges());
        System.out.println("Number of vertices : " + graph.numberOfVertices());
        System.out.println(graph.toGraphviz());
        System.out.println("Is 0 -> 1 an edge : " + graph.isEdge(0, 1) + " (expect True)");
        System.out.println("Is 0 -> 3 an edge : " + graph.isEdge(0, 3) + " (expect False)");
        System.out.println("Weight of 2 -> 0 edge " + graph.getWeight(2, 0) + " (expect 1)");
        System.out.println("Test Iterator :");
        IntStream.range(0, 4).forEach(
                i -> graph.forEachEdge(i, System.out::println)
        );
    }

    public static void main(String[] args) {
//        graphNFile();
//        testGraph(new MatGraph(4));
//        testGraph(new AdjGraph(4));
        var graphFromFile = Graph.makeGraphFromMatrixFile(Path.of("my_graph.mat"), MatGraph::new);
        Graph.saveGrapvizInFile(graphFromFile);
        System.out.println(Graphs.DFS(graphFromFile, 0));
        System.out.println(Graphs.DFS(graphFromFile, 1));
        System.out.println(Graphs.DFS(graphFromFile, 2));
        System.out.println(Graphs.DFS(graphFromFile, 3));
        System.out.println(Graphs.DFS(graphFromFile, 4));
        System.out.println(Graphs.DFS(graphFromFile, 5));
    }
}
