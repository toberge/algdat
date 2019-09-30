package com.toberge.data.graph;

import com.toberge.data.Queue;

import java.util.LinkedList;

public class BFSTree {

    private LinkedList<BFSNode> bfsNodes;
    private BFSNode root;

    /**
     * This is just like doing a BFS
     * but since it initializes a tree structure,
     * I can just as well make it the constructor.
     * @param graph
     * @param start
     */
    public BFSTree(Graph graph, Node start) {

        bfsNodes = new LinkedList<>();
        for (Object node : graph.getNodes()) {
            if (node != start) {
                bfsNodes.add(new BFSNode(start));
            }
        }
        // initialize start node
        root = new BFSNode(start);
        root.setDistance(0);
        // assemble queue
        Queue<BFSNode> queue = new Queue<>(bfsNodes.size() - 1);
        queue.put(root);
        while (!queue.isEmpty()) {
            BFSNode node = queue.getNext();
            if (node.getEdges() == null) continue;

            for (Edge edge : node.getEdges()) {
                // find neighbouring node
                BFSNode neighbor = (BFSNode) edge.getTo();
                if (neighbor.getDistance() == BFSNode.PSEUDO_INFINITY) {
                    // set distance if unset
                    neighbor.setDistance(node.getDistance() + 1);
                    neighbor.setParent(node);
                    queue.put(neighbor);
                }
            }
        }
    }
}
