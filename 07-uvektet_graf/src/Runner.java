import com.toberge.data.graph.*;
import com.toberge.timing.Tester;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;

public class Runner {

    private static BFSTree bfsTree;
    private static final int KALVSKINNET = 37774;
    private static final int MOHOLT = 18058;

    private static final int DRAMMEN = 65205;
    private static final int HELSINKI = 3378527;

    private static int distanceBetween(int start, int end) {
        bfsTree = null;
        Tester.test(() ->
            bfsTree = new BFSTree(new File("07-uvektet_graf/L7Skandinavia"), start)
        );
        return ((BFSNode)bfsTree.getGraph().getNodes().get(end)).getDistance();
    }

    private static void topoOrder(Graph<Node> graph) {
        System.out.println("||||Per-node metadata: ");
        Tester.test(graph::topoSort);
        Node node = graph.topoSort();
        StringBuilder builder = new StringBuilder();
        builder.append(node.getIndex());
        for (TopoData data = (TopoData) node.getMetadata(); data.getNext() != null;
             data = (TopoData) data.getNext().getMetadata()) {
            builder.append(" ");
            builder.append(data.getNext().getIndex());
        }
        //return builder.toString();
        System.out.println(builder);
    }

    private static void topoOrder2(Graph<Node> graph) {
        System.out.println("||||HashMap and LinkedList: ");
        Tester.test(graph::topoSort2);
        LinkedList<Node> order = graph.topoSort2();
        for (Node node : order) {
            System.out.print(node.getIndex() + " ");
        }
        System.out.println();
    }

    private static void topoOrder3(Graph<Node> graph) {
        System.out.println("||||boolean[] and LinkedList: ");
        Tester.test(graph::topoSort3);
        LinkedList<Node> order = graph.topoSort3();
        for (Node node : order) {
            System.out.print(node.getIndex() + " ");
        }
        System.out.println();
    }

    private static void presetDest() {
        System.out.println("Veikryss mellom Drammen og helsinki (distance - 1): " +
                (distanceBetween(DRAMMEN, HELSINKI) - 1));

        System.out.println("Veikryss mellom Helsinki og Drammen (distance - 1): " +
                (distanceBetween(HELSINKI, DRAMMEN) - 1));

        // Kalvskinnet @ 37774 --> Moholt @ 18058
        // 1979 ms, 2 secs:
        // 2154 ms, still 2 secs:
        System.out.println("Veikryss mellom Kalvskinnet og Moholt (distance - 1): " +
                (distanceBetween(KALVSKINNET, MOHOLT) - 1));
    }

    private static void customDest(Scanner scanner) {
        System.out.print("From: ");
        int from = scanner.nextInt();
        scanner.nextLine();
        System.out.print("To: ");
        int to = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Distanse: " + distanceBetween(from, to) + " (trekk fra 1 for ant. veikryss)");
    }

    public static void main(String[] args) {
        // BFS

        bfsTree = new BFSTree(new File("07-uvektet_graf/L7g1"), 5);
        System.out.println(bfsTree);
        bfsTree = new BFSTree(new File("07-uvektet_graf/L7g2"), 5);
        System.out.println(bfsTree);
//        bfsTree = new BFSTree(new File("07-uvektet_graf/L7g3"), 5);
//        System.out.println(bfsTree);
        bfsTree = new BFSTree(new File("07-uvektet_graf/L7g5"), 5);
        System.out.println(bfsTree);

        // TOPO

        Graph<Node> graph = GraphFactory.readFromFile(new File("07-uvektet_graf/L7g5"));
        topoOrder(graph);
        topoOrder2(graph);
        topoOrder3(graph);

        // TOPO OF CYCLIC GRAPH

        graph = GraphFactory.readFromFile(new File("07-uvektet_graf/L7g3"));
        topoOrder(graph);
        topoOrder2(graph);
        topoOrder3(graph);

        // MORE BFS

        Scanner scanner = new Scanner(System.in);

        System.out.print("Custom destinations? (y/n) ");
        String answer = scanner.nextLine();
        switch (answer.charAt(0)) {
            case 'y':
            case 'Y':
                customDest(scanner);
                break;
            default:
                presetDest();
                break;
        }

        System.out.print("Write BFSTree to file? (y/n) ");
        answer = scanner.nextLine();
        if (answer.charAt(0) == 'y' || answer.charAt(0) == 'Y') {
            Tester.test(() -> {
                try {
                    Files.write(Paths.get(System.getProperty("user.home") + "/.thingything"), bfsTree.toString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }
}
