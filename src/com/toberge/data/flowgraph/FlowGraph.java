package com.toberge.data.flowgraph;

import com.toberge.data.graph.Edge;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class FlowGraph {

    public static final int INFINITY = FlowNode.INFINITY;

    private LinkedList<FlowEdge>[] nodes;

    private int N, E;

    /**
     * Any parallel edges will be ignored.
     * The constructor creates additional edges where there is no opposite edge.
     */
    public FlowGraph(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine()); // entire damn file
        N = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        nodes = (LinkedList<FlowEdge>[]) new LinkedList[N];

        for (int i = 0; i < N; i++) {
            nodes[i] = new LinkedList<>();
        }

        // TODO fix opposites so they're only added if... yeah.
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int capacity = Integer.parseInt(st.nextToken());
            FlowEdge original = new FlowEdge(from, to, capacity);
            nodes[from].addFirst(original);

//            nodes[to].addFirst(original.getOppositeEdge());
        }

        // find existing opposites
        for (LinkedList<FlowEdge> edges : nodes) {
            for (FlowEdge edge : edges) {
                if (edge.getOppositeEdge() == null) {
                    // bind them together
                    FlowEdge opposite = findEdge(edge.getTo(), edge.getFrom());
                    if (opposite != null) {
                        System.out.println("opposite present at " + edge.getFrom() + " " + edge.getTo());
                        edge.setOppositeEdge(opposite);
                        opposite.setOppositeEdge(edge);
                    }
                }
            }
        }

        // find edges that have no opposite
        for (LinkedList<FlowEdge> edges : nodes) {
            for (FlowEdge edge : edges) {
                if (edge.getOppositeEdge() == null) {
                    // create opposite to that edge
                    FlowEdge opposite = new FlowEdge(edge.getTo(), edge.getFrom(), 0, edge);
                    edge.setOppositeEdge(opposite);
                    // THERE WE GOOOO
                    nodes[edge.getTo()].addFirst(opposite);
                }
            }
        }

    }

    public int getN() {
        return N;
    }

    public int getE() {
        return E;
    }

    /**
     * Implementation of the Edmonds-Karp algorithm
     * @param source of flow
     * @param sink where flow goes
     * @return stringified output
     */
    public String edmondsKarp(int source, int sink) {

        int increase, maxFlow = 0;
        StringBuilder builder = new StringBuilder("Max flow from " + source + " to " + sink + " with Edmonds-Karp");
        builder.append(System.lineSeparator());
        builder.append("Increase ; Augmenting path");

        do {
            builder.append(System.lineSeparator());
            // perform BFS to get an augmenting path
            LinkedList<FlowEdge> augmentingEdges = bfs(source, sink);

            // find max increase along that path
            increase = INFINITY;
            for (FlowEdge edge: augmentingEdges) {
                if (edge.remainingCapacity() < increase) increase = edge.remainingCapacity();
            }
            if (increase == INFINITY) increase = 0;
            maxFlow += increase;

            // calculate new flows & capacities
            if (augmentingEdges.size() > 0) {
                builder.append(String.format("%8d   ", increase));
                builder.append(augmentingEdges.getFirst().getFrom());
                for (FlowEdge edge : augmentingEdges) {
                    // System.out.print("Edge from " + edge.getFrom() + " to " + edge.getTo() + " flows " + edge.getFlow() + "/" + edge.getCapacity() + " now, ");
                    // direct flow through this edge
                    edge.addFlow(increase);
                    // System.out.println("up to " + edge.getFlow());
                    builder.append(" ");
                    builder.append(edge.getTo());
                }
            }
            // System.out.println("Done with this, incr. at " + increase);

        } while (increase > 0);

        builder.append("Maximal flow: ");
        builder.append(maxFlow);
        builder.append(System.lineSeparator());

        return builder.toString();
    }

    private FlowEdge findEdge(int from, int to) {
        for (FlowEdge edge : nodes[from]) {
            if (edge.getTo() == to) {
                return edge;
            }
        }
        return null;
    }

    private LinkedList<FlowEdge> toEdges(LinkedList<Integer> nodes) {
        LinkedList<FlowEdge> edges = new LinkedList<>();
        for (int i = 0; i < nodes.size() - 1; i++) {
            edges.add(findEdge(nodes.get(i), nodes.get(i+1)));
        }
        return edges;
    }

    /**
     * Performs a modified BFS that takes the remaining capacity of each edge into account.
     * @param start
     * @param end
     * @return the edges traversed in the augmenting path
     */
    private LinkedList<FlowEdge> bfs(int start, int end) {
        int[] distances = new int[N];
        int[] parents = new int[N];

        for (int i = 0; i < N; i++) {
            distances[i] = INFINITY;
            parents[i] = -1;
        }

        distances[start] = 0;
        ArrayDeque<Integer> queue = new ArrayDeque<>(N - 1);
        queue.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (FlowEdge edge : nodes[current]) {
                // skip if no leftover capacity
                if (edge.remainingCapacity() <= 0) continue;
                // if not, do regular BFS stuff w/distance
                int neighbor = edge.getTo();
                if (distances[neighbor] == INFINITY) {
                    distances[neighbor] = distances[current] + 1;
                    parents[neighbor] = current;
                    queue.add(neighbor);
                }
            }
        }

        LinkedList<Integer> result = new LinkedList<>();

        int i = parents[end];
        result.add(end);
        while (i > -1) {
            result.add(i);
            i = parents[i];
        }
        Collections.reverse(result);

        return toEdges(result);
    }
}
