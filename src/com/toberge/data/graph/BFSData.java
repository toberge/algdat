package com.toberge.data.graph;

public class BFSData extends ParentData {
    public static final int PSEUDO_INFINITY = 1000232323;
    private int distance = PSEUDO_INFINITY;

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
