package com.toberge.data.thatgraph;

@Deprecated
public class Edge {
    private Node to;
    private Edge next;

    public Edge(Node to, Edge next) {
        this.to = to;
        this.next = next;
    }

    public Node getTo() {
        return to;
    }

    public Edge getNext() {
        return next;
    }

    public void setNext(Edge next) {
        this.next = next;
    }
}
