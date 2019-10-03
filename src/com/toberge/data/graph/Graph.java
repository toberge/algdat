    package com.toberge.data.graph;

import java.util.*;

public class Graph<T extends Node> {
    private int N = 0, E = 0;
    private List<T> nodes = new ArrayList<>();

    public Graph() {

    }

    public Graph(int n, int e, T[] nodes) {
        if (n != nodes.length) throw new IllegalArgumentException("Warning: n =/= nodes.length, something's off!");
        N = n;
        E = e;
        Collections.addAll(this.nodes, nodes);
    }

    public List<T> getNodes() {
        return nodes;
    }

    public int getN() {
        return N;
    }

    public int getE() {
        return E;
    }

    public void addNode(T node) {
        if (!nodes.contains(node)) {
            nodes.add(node);
            N++;
        }
    }

    private void initTopo() {
        for (T node : nodes) {
            node.setMetadata(new TopoData());
        }
    }

    private Node topoDFS(Node start, Node currentResultHead) {
        TopoData data = (TopoData) start.getMetadata();
        if (data.isFound()) {
            // no need to DFS this, already gone down that path
            return currentResultHead;
        } else {
            data.markAsFound();
            if (start.hasEdges()) {
                for (Edge edge : start.getEdges()) {
                    // perform DFS from this neighbour
                    currentResultHead = topoDFS(edge.getTo(), currentResultHead);
                }
            }
            data.setNext(currentResultHead);
            return start;
        }
    }

    public Node topoSort() {
       initTopo();
       Node currentResultHead = null;
       for (Node node : nodes) {
           currentResultHead = topoDFS(node, currentResultHead);
       }
       return currentResultHead;
    }

    private void topoDFS2(Node start, LinkedList<Node> order, HashMap<Node, Boolean> visited) {
        if (!visited.get(start)) {
            visited.put(start, true);
            if (start.hasEdges()) {
                for (Edge edge : start.getEdges()) {
                    topoDFS2(edge.getTo(), order, visited);
                }
            }
            order.addFirst(start);
        }
        // else do not modify order
    }

    public LinkedList<Node> topoSort2() {
        LinkedList<Node> order = new LinkedList<>();
        HashMap<Node, Boolean> visited = new HashMap<>();
        for (Node node : nodes) {
            visited.put(node, false);
        }
        for (Node node : nodes) {
            topoDFS2(node, order, visited);
        }
        return order;
    }

    private void topoDFS3(Node start, LinkedList<Node> order, boolean[] visited) {
        if (!visited[start.getIndex()]) {
            visited[start.getIndex()] = true;
            if (start.hasEdges()) {
                for (Edge edge : start.getEdges()) {
                    topoDFS3(edge.getTo(), order, visited);
                }
            }
            order.addFirst(start);
        }
        // else do not modify order
    }

    public LinkedList<Node> topoSort3() {
        LinkedList<Node> order = new LinkedList<>();
        boolean[] visited = new boolean[N];
        for (Node node : nodes) {
            topoDFS3(node, order, visited);
        }
        return order;
    }
}
