package com.toberge.data.thatgraph;

import com.toberge.data.Queue;

public class BFSTree {

    private BFSNode[] bfsNodes;
    private BFSNode root;

    public BFSTree(Graph graph, Node start) {
        bfsNodes = new BFSNode[graph.getNumNodes()];
        for (int i = 0; i < graph.getNumNodes(); i++) {
            if (graph.getNodes().get(i) != start) {
                bfsNodes[i] = new BFSNode(graph.getNodes().get(i));
            }
        }
        // initialize start node
        root = new BFSNode(start);
        root.setDistance(0);
        // assemble queue
        Queue<BFSNode> queue = new Queue<>(bfsNodes.length - 1);
        queue.put(root);
        while (!queue.isEmpty()) {
            BFSNode node = queue.getNext();
            for (Edge edge = node.getFirstEdge(); edge != null; edge = edge.getNext()) {
                // find neighbouring node
                BFSNode neighbor = (BFSNode) edge.getTo();
                if (neighbor.getDistance() == BFSNode.PSEUDO_INFINITY) {
                    // set distance if unset
                    neighbor.setDistance(node.getDistance() + 1);
                    neighbor.setParent(node);
                    queue.put((BFSNode) edge.getTo());
                }
            }
        }
    }

    public BFSNode[] getBfsNodes() {
        return bfsNodes;
    }

    public BFSNode getRoot() {
        return root;
    }
}
