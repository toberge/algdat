import com.toberge.data.Queue;
import com.toberge.data.thatgraph.Edge;
import com.toberge.data.thatgraph.Graph;
import com.toberge.data.thatgraph.Node;

import java.util.Scanner;

@Deprecated
public class Dominoes2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cases = scanner.nextInt();
        int[] counts = new int[cases];
        scanner.nextLine();
        for (int i = 0; i < cases; i++) {
            int dominoes = scanner.nextInt(),
                   edges = scanner.nextInt(),
                 topples = scanner.nextInt();
            int fallen = 0;
            Graph graph = new Graph();
            scanner.nextLine();
            // initialize graph
            for (int j = 0; j < dominoes; j++) {
                graph.addNode(new Domino(j+1, null));
            }
            // read domino relations
            for (int j = 0; j < edges; j++) {
                int fromi = scanner.nextInt() - 1;
                int toi = scanner.nextInt() - 1;
                Domino from = (Domino) graph.getNodes().get(fromi);
                from.addEdge(graph.getNodes().get(toi));
                scanner.nextLine();
            }
            // read toppling status
            Domino[] toppled = new Domino[topples];
            for (int j = 0; j < topples; j++) {
                toppled[i] = (Domino) graph.getNodes().get(scanner.nextInt() - 1);
                toppled[i].topple();
                fallen++;
                scanner.nextLine();
            }

            Queue<Domino> queue = new Queue<>(dominoes * 2);
            for (Domino domino:
                 toppled) {
                queue.put(domino);
            }
            while (!queue.isEmpty()) {
                Domino domino = queue.getNext();
                domino.topple();
                //fallen++;
                for (Edge edge = domino.getFirstEdge(); edge != null; edge = edge.getNext()) {
                    Domino adjacent = ((Domino) edge.getTo());
                    if (!adjacent.hasFallen()) {
                        adjacent.topple();
                        fallen++;
                        queue.put(adjacent);
                    }
                }
            }

            counts[i] = fallen;
        }
        for (int count : counts) {
            System.out.println(count);
        }
    }
}

class Domino extends Node {
    private boolean fallen = false;
    private int id;

    public Domino(int id, Edge firstEdge) {
        super(firstEdge);
        this.id = id;
    }

    public void topple() {
        fallen = true;
    }

    public boolean hasFallen() {
        return fallen;
    }
}