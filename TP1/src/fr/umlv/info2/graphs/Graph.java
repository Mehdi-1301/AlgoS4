package fr.umlv.info2.graphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public interface Graph {
    int numberOfEdges();
    int numberOfVertices();
    void addEdge(int i, int j, int value);
    boolean isEdge(int i, int j);
    int getWeight(int i, int j);

    Iterator<Edge> edgeIterator(int i);
    default void forEachEdge(int i, Consumer<Edge> consumer) {
        if (i >= numberOfVertices()) {
            throw new IllegalArgumentException();
        }
        edgeIterator(i).forEachRemaining(consumer);
    }
    default String toGraphviz() {
        var stringBuilder = new StringBuilder("digraph G {\n");
        IntStream.range(0, numberOfVertices()).forEach(
                i -> {
                    stringBuilder.append('\t');
                    stringBuilder.append(i);
                    stringBuilder.append(";\n");
                    forEachEdge(i, edge -> {
                        stringBuilder.append('\t');
                        stringBuilder.append(i);
                        stringBuilder.append(" -> ");
                        stringBuilder.append(edge.getEnd());
                        stringBuilder.append(" [label=\"");
                        stringBuilder.append(edge.getValue());
                        stringBuilder.append("\" ] ;\n");
                    });
                }
        );
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    static Graph makeGraphFromMatrixFile(Path path, IntFunction<Graph> factory) {
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            var lines = reader.lines().toArray(String[]::new);
            if (lines.length == 0) {
                throw new IllegalArgumentException();
            }
            var numberOFVertices = Integer.parseInt(lines[0]);
            var graph = factory.apply(numberOFVertices);
            for (int i = 1; i < numberOFVertices + 1; i++) {
                var line = lines[i].split(" ");
                for (int j = 0; j < numberOFVertices; j++) {
                    var value = Integer.parseInt(line[j]);
                    if (value != 0) {
                        graph.addEdge(i - 1, j, value);
                    }
                }
            }
            return graph;
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    static void saveGrapvizInFile(Graph graph, String fileName) {
        try {
            Files.write(Path.of(fileName),
                    List.of(graph.toGraphviz()),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Graph saved " + fileName);
        } catch (IOException e) {
            System.out.println("Impossible to save graph as file");
        }
    }

    static void saveGrapvizInFile(Graph graph) {
        saveGrapvizInFile(graph, "input.dot");
    }

    static void randdomGraph(int numberOfVertice, IntFunction<Graph> factory) {
        var graph = factory.apply(numberOfVertice);
        var range = (numberOfVertice * numberOfVertice) - numberOfVertice + 1;
        var edgeNumber = Double.valueOf((Math.random() * range ) - numberOfVertice).intValue();
        IntStream.range(0, edgeNumber).forEach(i -> {
            graph.addEdge(
                    Double.valueOf(Math.random() * numberOfVertice).intValue(),
                    Double.valueOf(Math.random() * numberOfVertice).intValue(),
                    Double.valueOf(Math.random() * numberOfVertice).intValue());
        });
    }
}
