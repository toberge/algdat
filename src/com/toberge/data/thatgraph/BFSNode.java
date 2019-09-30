package com.toberge.data.thatgraph;

public class BFSNode extends Node {
    public static final int PSEUDO_INFINITY = 1000232323;
    private BFSNode parent = null;
    private int distance;

    // clone constructor
    public BFSNode(Node node) {
        super(node.getFirstEdge());
        this.distance = PSEUDO_INFINITY;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public BFSNode getParent() {
        return parent;
    }

    public void setParent(BFSNode parent) {
        this.parent = parent;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
