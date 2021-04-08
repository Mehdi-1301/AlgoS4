package fr.umlv.info2.graphs;

import java.util.Objects;

public class Edge {
    private final int start;
    private final int end;
    private final int value;

    public Edge(int start, int end, int value) {
        this.start = start;
        this.end = end;
        this.value = value;
    }

    public Edge(int start, int end) {
        this(start, end, 1);
    }

    public int getValue() {
        return value;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return start + " -- " + end + " ( " + value + " )";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return start == edge.start && end == edge.end && value == edge.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, value);
    }
}