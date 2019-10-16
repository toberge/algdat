package com.toberge.data.flowgraph;

import com.toberge.data.LinkedList;

public class FlowNode {

    public static final int INFINITY = 1000232323;

    private int index;
    private int distance = INFINITY;

    private LinkedList<FlowEdge> edges = new LinkedList<>();

    public FlowNode(int index) {
        this.index = index;
    }
}
