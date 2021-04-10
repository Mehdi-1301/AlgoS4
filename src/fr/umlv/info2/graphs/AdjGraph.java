package fr.umlv.info2.graphs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Consumer;

public class AdjGraph implements Graph{

    private final ArrayList<LinkedList<Edge>> adj = new ArrayList<>();
    private final int n;
    private int nEdges = 0;

    public AdjGraph(int nVertices)
    {
        if(nVertices <= 0)
            throw new IllegalArgumentException("n must be positive");

        this.n = nVertices;
        for(int i = 0; i < nVertices; i++)
        {
            var ll = new LinkedList<Edge>();
            adj.add(ll);
        }
    }

    @Override
    public int numberOfEdges() {
        return nEdges;
    }

    @Override
    public int numberOfVertices() {
        return n;
    }

    @Override
    public void addEdge(int i, int j, int value) {
        Objects.checkIndex(i,n);
        Objects.checkIndex(j,n);
        if(value == 0)
            throw new IllegalArgumentException("The value can't equal 0");
        nEdges++;
        var ll = adj.get(i);
        ll.add(new Edge(i,j,value));
    }

    @Override
    public boolean isEdge(int i, int j) {
        Objects.checkIndex(i,n);
        Objects.checkIndex(j,n);
        var ll = adj.get(i);
        for (Edge edge : ll)
        {
            if (edge.getStart() == i && edge.getEnd() == j)
                return true;
        }
        return false;
    }

    @Override
    public int getWeight(int i, int j) {
        Objects.checkIndex(i,n);
        Objects.checkIndex(j,n);
        if(!isEdge(i,j))
            throw new IllegalArgumentException("Edge(i,j) doesn't exist");

        var ll = adj.get(i);
        var value = -1;
        for (Edge edge : ll)
        {
            if (edge.getStart() == i && edge.getEnd() == j)
                value = edge.getValue();
        }
        return value;
    }


    @Override
    public Iterator<Edge> edgeIterator(int i) {
        Objects.checkIndex(i,n);
        return adj.get(i).iterator();
    }

    @Override
    public void forEachEdge(int i, Consumer<Edge> consumer) {
        Objects.checkIndex(i,n);
        edgeIterator(i).forEachRemaining(consumer);
    }

    @Override
    public String toGraphviz() {
        StringBuilder stringBuilder = new StringBuilder("digraph G {\n");
        for (int i = 0; i < adj.size(); i++)
        {
            stringBuilder.append(i).append(";\n");
            Iterator<Edge> edgeIterator = edgeIterator(i);
            edgeIterator.forEachRemaining(edge -> stringBuilder.append(edge.getStart()).append(" -> ")
                    .append(edge.getEnd()).append(" [ label=\"").append(edge.getValue()).append("\" ] ;\n"));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
