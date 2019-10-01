package com.toberge.data.graph;

import com.toberge.data.Queue;

import java.io.File;

public class BFSTree {

    private Graph graph;
    private BFSNode root;

    /**
     * This is just like doing a BFS
     * but since it initializes a tree structure,
     * I can just as well make it the constructor.
     * @param file
     * @param start
     */
    public BFSTree(File file, int start) {

        graph = GraphFactory.readFromFile(file, BFSNode::new);

        /*Graph<BFSNode> bfsGraph = new Graph<>();
        for (Node node : graph.getNodes()) {
            BFSNode newborn = new BFSNode(node.getIndex());
            bfsGraph.addNode(newborn);
            if (bfsGraph.getNodes().get(newborn.getIndex()) != newborn) {
                System.err.println("FUCK");
                System.exit(1);
            }
            if (node != start) {
                bfsNodes.add(new BFSNode(start));
            }

        }
        for (BFSNode node : bfsGraph.getNodes()) {
            // TODO here or elsewhere?
        }*/

        root = performBFS(graph, start);

    }

    public static BFSNode performBFS(Graph graph, int start) {
        // initialize start node
        //root = new BFSNode(start);
        BFSNode root = (BFSNode) graph.getNodes().get(start);
        root.setDistance(0);
        // assemble queue
        Queue<BFSNode> queue = new Queue<>(graph.getN() - 1);
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
        return root;
    }

    public Graph getGraph() {
        return graph;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(" Node \tParent\tDistance");
        builder.append(System.lineSeparator());
        for (Object node: graph.getNodes()) {
            builder.append(node);
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }
}
