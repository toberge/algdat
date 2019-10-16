package com.toberge.data.flowgraph;

public class FlowEdge {

    private int from, to;

    private int capacity, flow = 0;

    private FlowEdge oppositeEdge;

    public FlowEdge(int from, int to, int capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.oppositeEdge = null;
//        this.oppositeEdge = new FlowEdge(to, from, 0, this);
    }

    public FlowEdge(int from, int to, int capacity, FlowEdge oppositeEdge) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.oppositeEdge = oppositeEdge;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFlow() {
        return flow;
    }

    /**
     * Direct flow through this edge; divert flow from the opposite edge.
     * @param flow
     */
    public void addFlow(int flow) {
        this.flow += flow;
        this.oppositeEdge.flow -= flow;
    }

    public FlowEdge getOppositeEdge() {
        return oppositeEdge;
    }

    public void setOppositeEdge(FlowEdge oppositeEdge) {
        this.oppositeEdge = oppositeEdge;
    }

    public int remainingCapacity() {
        return capacity - flow;
    }
}
