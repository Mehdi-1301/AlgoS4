package fr.umlv.info2.graphs.main;

import fr.umlv.info2.graphs.AdjGraph;
import fr.umlv.info2.graphs.Graph;
import fr.umlv.info2.graphs.Graphs;

import java.io.IOException;
import java.nio.file.Path;

public class AdjGraphTest {
    public static void main(String[] args) throws IOException {
        Graph graph = Graphs.readGraphFromFile(Path.of("graph.mat"));
        Graphs.writeInFile(Path.of("input.dot"), graph.toGraphviz());
    }
}
