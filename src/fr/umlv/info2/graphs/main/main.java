package fr.umlv.info2.graphs.main;

import fr.umlv.info2.graphs.Graph;
import fr.umlv.info2.graphs.Graphs;
import fr.umlv.info2.graphs.MatGraph;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

public class main {

    public static void main(String[] args) throws IOException {
        MatGraph matGraph = new MatGraph(8);
        matGraph.addEdge(1, 4, 1);
        matGraph.addEdge(2, 6, 1);
        matGraph.addEdge(2, 7, 1);
        matGraph.addEdge(3, 6, 1);
        matGraph.addEdge(0, 1, 1);
        matGraph.addEdge(0, 2, 1);
        matGraph.addEdge(0, 3, 1);



        Graphs.BFS(matGraph,0).forEach(System.out::print);

        Graphs.writeInFile(Path.of("input.dot"), matGraph.toGraphviz());
    }



}
