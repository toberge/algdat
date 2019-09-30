package com.toberge.data.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph<T extends Node> {
    private int N = 0, E = 0;
    private LinkedList<T> nodes = new LinkedList<>();

    public Graph() {

    }

    public Graph(int n, int e, T[] nodes) {
        if (n != nodes.length) throw new IllegalArgumentException("Warning: n =/= nodes.length, something's off!");
        N = n;
        E = e;
        this.nodes = new LinkedList<>();
        Collections.addAll(this.nodes, nodes);
    }

    public Graph(File file) {

    }

    public LinkedList<T> getNodes() {
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

    /**
     *
     * Format:
     * V E --> V nodes, E edges
     * f t --> E edges from node f to node t
     *
     * @param file
     * @return graph
     */
    public static Graph<Node> readFromFile(File file) {
        Graph<Node> graph = null;

        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {

            StringTokenizer st = new StringTokenizer(br.readLine()); // entire damn file
            int N = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());

            Node[] nodes = new Node[N];
            for (int i = 0; i < N; i++) {
                nodes[i] = new Node(i);
            }

            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                Edge edge = new Edge(nodes[to]);
                nodes[from].getEdges().add(edge);
            }

            graph = new Graph<>(N, E, nodes);


        } catch (IOException | NumberFormatException | NoSuchElementException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException damnSeriousException) {
            System.err.println("what on Earth just happened");
            damnSeriousException.printStackTrace();
            System.err.println("good heavens would you look at the time");
        } catch (NullPointerException npe) {
            System.err.println("Failed to load yer file");
            npe.printStackTrace();
        }

        return graph;

    }

    public static Graph<Node> readFromFile(File file, Nodifier nodifier) {
        Graph<Node> graph = null;

        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {

            StringTokenizer st = new StringTokenizer(br.readLine()); // entire damn file
            int N = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());

            Node[] nodes = new Node[N];
            for (int i = 0; i < N; i++) {
                nodes[i] = nodifier.create(i);
                //System.out.println(nodes[i]);
            }

            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                //System.out.println(from + " " + to);
                nodes[from].addEdge(nodes[to]);
            }

            graph = new Graph<>(N, E, nodes);


        } catch (IOException | NumberFormatException | NoSuchElementException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException damnSeriousException) {
            System.err.println("what on Earth just happened");
            damnSeriousException.printStackTrace();
            System.err.println("good heavens would you look at the time");
        } catch (NullPointerException npe) {
            System.err.println("Failed to load yer file");
            npe.printStackTrace();
        }

        return graph;

    }
}
