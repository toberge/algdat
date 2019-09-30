package com.toberge.data.thatgraph;

@Deprecated
public class Node {
    private Edge firstEdge;

    public Node(Edge firstEdge) {
        this.firstEdge = firstEdge;
    }

    public Edge getFirstEdge() {
        return firstEdge;
    }

    public void addEdge(Node to) {
        if (firstEdge == null) {
            firstEdge = new Edge(to, null);
        } else {
            Edge edge = firstEdge;
            while (edge.getNext() != null) {
                edge = edge.getNext();
            }
            edge.setNext(new Edge(to, null));
        }
    }
}
