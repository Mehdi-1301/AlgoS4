package fr.umlv.info2.graphs;

import java.util.Iterator;
import java.util.function.Consumer;

public interface Graph {
    int numberOfEdges();

    int numberOfVertices();

    void addEdge(int i, int j, int value);

    boolean isEdge(int i, int j);

    int getWeight(int i, int j);

    Iterator<Edge> edgeIterator(int i);

    void forEachEdge(int i, Consumer<Edge> consumer);

    String toGraphviz();

    default Graph transpose() {
        Graph newGraph = new AdjGraph(this.numberOfVertices());
        for (int i = 0; i < this.numberOfVertices(); i++) {
            this.forEachEdge(i, edge -> {
                newGraph.addEdge(edge.getEnd(), edge.getStart(), edge.getValue());
            });
        }
        return newGraph;
    }
}
