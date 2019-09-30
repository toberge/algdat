package com.toberge.data.graph;

public class Edge {
    private Node to;

    public Edge(Node to) {
        if (to == null) throw new IllegalArgumentException("Edge points to *nothing*, wtf!");
        this.to = to;
    }

    public Node getTo() {
        return to;
    }
}
