package com.toberge.data.graph;

import java.util.LinkedList;

public class Node {
    private LinkedList<Edge> edges = null;
    private int index;

    private Metadata metadata;

    public Node() {
    }

    public Node(int index) {
        this.index = index;
    }

    /*public Node(Node node) {
        this.edges = node.edges;
    }*/

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public int getIndex() {
        return index;
    }

    public boolean hasEdges() {
        return !(edges == null || edges.size() == 0); // TODO possible NULL
    }

    public LinkedList<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Node to) {
        if (edges == null) {
            edges = new LinkedList<>();
        }
        // TODO decide first or last
        edges.addFirst(new Edge(to));
    }
}
