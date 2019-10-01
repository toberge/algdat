package com.toberge.data.graph;

import java.io.File;
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

    public Graph(File file) {

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

    // do I even need this method
    public void addEdge(T from, T to) {
        /*int i = nodes.indexOf(from);
        if (i > -1) {
            nodes.get(i).addEdge(to);
            E++;
        }*/
        // TODO maybe throw exception instead (or just ignore)
        /*if (!nodes.contains(from)) {
            nodes.add(from);
            N++;
        }
        if (!nodes.contains(to)) {
            nodes.add(to);
            N++;
        }*/
        from.addEdge(to);
        E++;
    }


}
