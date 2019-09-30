package com.toberge.data.graph;

import java.util.LinkedList;

public class Node {
    private LinkedList<Edge> edges = null;

    public Node() {
    }

    /**
     * Clone constructor that <em>should not</em>
     * be used outside of BFS/DFS-esque operations
     * since the edges are preserved
     * @param node
     */
    public Node(Node node) {
        this.edges = node.edges;
    }

    public LinkedList<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Node to) {
        if (edges == null) {
            edges = new LinkedList<>();
        }
        edges.add(new Edge(to));
    }
}
