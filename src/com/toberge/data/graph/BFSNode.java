package com.toberge.data.graph;

public class BFSNode extends Node {
    public static final int PSEUDO_INFINITY = 1000232323;
    private BFSNode parent = null;
    private int distance = PSEUDO_INFINITY;

    public BFSNode(int i) {
        super(i);
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

    @Override
    public String toString() {
        String res = String.format("%6d\t", getIndex());
        res += hasParent()
                ? String.format("%6d\t", parent.getIndex())
                : "      \t";
        res += String.format("%6d", distance);
        return res;
    }
}
