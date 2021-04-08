package fr.umlv.info2.graphs.main;

import fr.umlv.info2.graphs.Graph;
import fr.umlv.info2.graphs.Graphs;
import fr.umlv.info2.graphs.MatGraph;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

public class MatGraphTest {
    public static void main(String[] args) throws IOException {
        MatGraph matGraph = new MatGraph(6);
        matGraph.addEdge(1, 0, 1);
        matGraph.addEdge(2, 1, 1);
        matGraph.addEdge(0, 3, 1);
        matGraph.addEdge(3, 1, 1);
        matGraph.addEdge(3, 4, 1);
        matGraph.addEdge(4, 1, 1);
        matGraph.addEdge(4, 3, 1);
        matGraph.addEdge(5, 4, 1);
        matGraph.addEdge(5, 2, 1);
        matGraph.addEdge(2, 5, 1);
        Graphs.writeInFile(Path.of("input.dot"), matGraph.toGraphviz());
        System.out.println(Graphs.scc(matGraph));
                /*
        int[][] ints = Graphs.timedDepthFirstSearch(matGraph, 0);
        for (int i = 0; i < ints.length; i++) {
            System.out.println("Node:"+i+" = start :"+ints[i][0]+" end :"+ints[i][1]);
        }

                 */


    }
}
