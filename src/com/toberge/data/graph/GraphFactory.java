package com.toberge.data.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class GraphFactory {

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
       return readFromFile(file, Node::new);
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
