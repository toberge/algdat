package com.toberge.data.thatgraph;

import java.util.ArrayList;

@Deprecated
public class Graph {
    private int numNodes, numEdges;
    private ArrayList<Node> nodes = new ArrayList<>();

    public int getNumNodes() {
        return numNodes;
    }

    public int getNumEdges() {
        return numEdges;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
    public Node[] getNodesAsArray() {
        return (Node[]) nodes.toArray();
    }

    public void addNode(Node node) {
        nodes.add(node);
    }
}
